package com.biblioteca.ms_multas.service;

import com.biblioteca.ms_multas.client.PrestamoClient;
import com.biblioteca.ms_multas.client.UserClient;
import com.biblioteca.ms_multas.dto.PrestamoDto;
import com.biblioteca.ms_multas.dto.UsuarioDto;
import com.biblioteca.ms_multas.model.Multa;
import com.biblioteca.ms_multas.repository.MultaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MultaService {

    private static final Logger log = LoggerFactory.getLogger(MultaService.class);

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_PAGADA = "PAGADA";
    private static final String ESTADO_ANULADA = "ANULADA";

    @Autowired
    private MultaRepository repository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PrestamoClient prestamoClient;

    @Value("${multas.tarifa-por-dia:500}")
    private BigDecimal tarifaPorDia;

    /**
     * Calcula y registra una multa para un prestamo dado.
     * Reglas:
     *  - Si el prestamo no existe -> error de negocio.
     *  - Si no esta vencido (fechaDevolucion >= hoy) -> error de negocio.
     *  - Si ya existe una multa registrada para ese prestamo -> error de negocio.
     */
    public Multa calcularMulta(Long prestamoId) {
        log.info("Iniciando calculo de multa para prestamoId={}", prestamoId);

        PrestamoDto prestamo;
        try {
            prestamo = prestamoClient.obtenerPorId(prestamoId);
            log.debug("Feign ms-prestamos OK prestamoId={}", prestamoId);
        } catch (FeignException.NotFound ex) {
            log.warn("Prestamo no encontrado prestamoId={}", prestamoId);
            throw new IllegalArgumentException("Prestamo no encontrado en ms-prestamos");
        } catch (FeignException ex) {
            log.error("Fallo Feign ms-prestamos status={} message={}", ex.status(), ex.getMessage());
            throw new IllegalStateException("Error comunicandose con ms-prestamos", ex);
        }

        if (prestamo == null) {
            log.warn("Prestamo respondio null prestamoId={}", prestamoId);
            throw new IllegalArgumentException("Prestamo no encontrado en ms-prestamos");
        }

        if (prestamo.getFechaDevolucion() == null) {
            log.warn("Prestamo sin fechaDevolucion prestamoId={}", prestamoId);
            throw new IllegalArgumentException(
                    "El prestamo no tiene fecha de devolucion programada");
        }

        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaDevolucion(), LocalDate.now());
        log.info("Dias de retraso calculados={} prestamoId={}", diasRetraso, prestamoId);

        if (diasRetraso <= 0) {
            log.info("Prestamo aun vigente, no se genera multa prestamoId={}", prestamoId);
            throw new IllegalArgumentException(
                    "El prestamo aun no esta vencido, no se puede calcular multa");
        }

        if (repository.existsByPrestamoId(prestamoId)) {
            log.warn("Multa duplicada para prestamoId={}", prestamoId);
            throw new IllegalArgumentException(
                    "Ya existe una multa registrada para el prestamo " + prestamoId);
        }

        try {
            UsuarioDto usuario = userClient.obtenerPorEmail(prestamo.getEmailUsuario());
            if (usuario == null) {
                throw new IllegalArgumentException("Usuario asociado al prestamo no encontrado");
            }
            log.debug("Feign user-service OK email={}", prestamo.getEmailUsuario());
        } catch (FeignException.NotFound ex) {
            log.warn("Usuario no encontrado al multar prestamoId={} email={}",
                    prestamoId, prestamo.getEmailUsuario());
            throw new IllegalArgumentException("Usuario asociado al prestamo no encontrado");
        } catch (FeignException ex) {
            log.error("Fallo Feign user-service status={} message={}", ex.status(), ex.getMessage());
            throw new IllegalStateException("Error comunicandose con user-service", ex);
        }

        Multa multa = new Multa();
        multa.setEmailUsuario(prestamo.getEmailUsuario());
        multa.setPrestamoId(prestamoId);
        multa.setDiasRetraso((int) diasRetraso);
        multa.setMonto(tarifaPorDia.multiply(BigDecimal.valueOf(diasRetraso)));
        multa.setFechaGeneracion(LocalDate.now());
        multa.setEstado(ESTADO_PENDIENTE);

        Multa guardada = repository.save(multa);
        log.info("Multa creada id={} prestamoId={} email={} monto={} dias={}",
                guardada.getId(), prestamoId, guardada.getEmailUsuario(),
                guardada.getMonto(), guardada.getDiasRetraso());
        return guardada;
    }

    public Multa pagarMulta(Long multaId) {
        log.info("Pagando multa id={}", multaId);
        Multa multa = repository.findById(multaId)
                .orElseThrow(() -> {
                    log.warn("Pago intentado sobre multa inexistente id={}", multaId);
                    return new IllegalArgumentException("Multa no encontrada");
                });

        if (ESTADO_PAGADA.equals(multa.getEstado())) {
            log.warn("Pago duplicado sobre multa id={}", multaId);
            throw new IllegalArgumentException("La multa ya esta pagada");
        }
        if (ESTADO_ANULADA.equals(multa.getEstado())) {
            log.warn("Intento de pagar multa anulada id={}", multaId);
            throw new IllegalArgumentException("La multa esta anulada, no se puede pagar");
        }

        multa.setEstado(ESTADO_PAGADA);
        multa.setFechaPago(LocalDate.now());
        Multa pagada = repository.save(multa);
        log.info("Multa pagada id={} email={}", pagada.getId(), pagada.getEmailUsuario());
        return pagada;
    }

    /**
     * Actualizacion administrativa de los dias de retraso o el monto manual de una multa.
     * Permite recalcular o ajustar antes del pago.
     */
    public Multa ajustarMulta(Long multaId, Integer nuevosDiasRetraso, BigDecimal nuevoMonto) {
        log.info("Ajustando multa id={} dias={} monto={}", multaId, nuevosDiasRetraso, nuevoMonto);
        Multa multa = repository.findById(multaId)
                .orElseThrow(() -> new IllegalArgumentException("Multa no encontrada"));

        if (!ESTADO_PENDIENTE.equals(multa.getEstado())) {
            log.warn("Ajuste rechazado: multa id={} no esta PENDIENTE (estado={})",
                    multaId, multa.getEstado());
            throw new IllegalArgumentException("Solo se pueden ajustar multas en estado PENDIENTE");
        }

        if (nuevosDiasRetraso != null && nuevosDiasRetraso > 0) {
            multa.setDiasRetraso(nuevosDiasRetraso);
            multa.setMonto(tarifaPorDia.multiply(BigDecimal.valueOf(nuevosDiasRetraso)));
        }
        if (nuevoMonto != null && nuevoMonto.compareTo(BigDecimal.ZERO) >= 0) {
            multa.setMonto(nuevoMonto);
        }

        Multa actualizada = repository.save(multa);
        log.info("Multa ajustada id={} dias={} monto={}",
                actualizada.getId(), actualizada.getDiasRetraso(), actualizada.getMonto());
        return actualizada;
    }

    /**
     * Anula una multa (soft delete logico) por error de calculo, condonacion, etc.
     */
    public Multa anularMulta(Long multaId) {
        log.info("Anulando multa id={}", multaId);
        Multa multa = repository.findById(multaId)
                .orElseThrow(() -> new IllegalArgumentException("Multa no encontrada"));

        if (ESTADO_ANULADA.equals(multa.getEstado())) {
            throw new IllegalArgumentException("La multa ya estaba anulada");
        }

        multa.setEstado(ESTADO_ANULADA);
        Multa anulada = repository.save(multa);
        log.info("Multa anulada id={} email={}", anulada.getId(), anulada.getEmailUsuario());
        return anulada;
    }

    /**
     * Eliminacion fisica (DELETE real). Recomendado solo para correcciones puntuales.
     */
    public void eliminarMulta(Long multaId) {
        log.warn("Eliminacion fisica de multa id={}", multaId);
        if (!repository.existsById(multaId)) {
            throw new IllegalArgumentException("Multa no encontrada");
        }
        repository.deleteById(multaId);
        log.info("Multa eliminada definitivamente id={}", multaId);
    }

    public List<Multa> listarPorUsuario(String email) {
        log.debug("Listando multas de email={}", email);
        return repository.findByEmailUsuario(email);
    }

    public List<Multa> listarPendientesPorUsuario(String email) {
        return repository.findByEmailUsuarioAndEstado(email, ESTADO_PENDIENTE);
    }

    public boolean tienePendientes(String email) {
        boolean pendientes = repository.existsByEmailUsuarioAndEstado(email, ESTADO_PENDIENTE);
        log.debug("tienePendientes email={} resultado={}", email, pendientes);
        return pendientes;
    }

    public Multa buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Multa> listarTodas() {
        return repository.findAll();
    }
}
