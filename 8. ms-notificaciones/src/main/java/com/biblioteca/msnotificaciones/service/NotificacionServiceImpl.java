package com.biblioteca.msnotificaciones.service;

import com.biblioteca.msnotificaciones.client.PrestamoClient;
import com.biblioteca.msnotificaciones.client.UserClient;
import com.biblioteca.msnotificaciones.dto.*;
import com.biblioteca.msnotificaciones.entity.Notificacion;
import com.biblioteca.msnotificaciones.exception.ResourceNotFoundException;
import com.biblioteca.msnotificaciones.repository.NotificacionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;
    private final UserClient userClient;
    private final PrestamoClient prestamoClient;

    private void validarUsuario(String email) {
        try {
            UsuarioDto u = userClient.obtenerPorEmail(email);
            if (u == null) throw new ResourceNotFoundException("Usuario no encontrado en user-service");
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Usuario no encontrado en user-service");
        } catch (FeignException ex) {
            log.error("Feign user-service: {}", ex.getMessage());
            throw new IllegalStateException("Error comunicandose con user-service");
        }
    }

    @Override
    public NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO dto) {
        log.info("Crear notificacion tipo={} para email={}", dto.getTipo(), dto.getEmailUsuario());
        validarUsuario(dto.getEmailUsuario());

        Notificacion n = Notificacion.builder()
                .emailUsuario(dto.getEmailUsuario())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .fechaEnvio(LocalDateTime.now())
                .estado("PENDIENTE")
                .recursoId(dto.getRecursoId())
                .build();
        Notificacion saved = repository.save(n);
        log.info("Notificacion creada id={}", saved.getId());
        return mapToResponse(saved);
    }

    /**
     * Genera una notificacion de vencimiento proximo basada en un prestamo.
     * Calcula los dias restantes consultando ms-prestamos via Feign.
     */
    @Override
    public NotificacionResponseDTO notificarVencimiento(Long prestamoId) {
        log.info("Notificar vencimiento para prestamoId={}", prestamoId);
        PrestamoDto prestamo;
        try {
            prestamo = prestamoClient.obtenerPorId(prestamoId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Prestamo no encontrado en ms-prestamos");
        } catch (FeignException ex) {
            log.error("Feign ms-prestamos: {}", ex.getMessage());
            throw new IllegalStateException("Error comunicandose con ms-prestamos");
        }
        if (prestamo == null || prestamo.getFechaDevolucion() == null) {
            throw new ResourceNotFoundException("Prestamo sin fecha de devolucion");
        }

        long dias = ChronoUnit.DAYS.between(LocalDate.now(), prestamo.getFechaDevolucion());
        String mensaje = "Tu prestamo (libro " + prestamo.getLibroId() + ") vence en " + dias + " dia(s)";

        Notificacion n = Notificacion.builder()
                .emailUsuario(prestamo.getEmailUsuario())
                .mensaje(mensaje)
                .tipo("VENCIMIENTO_PROXIMO")
                .fechaEnvio(LocalDateTime.now())
                .estado("PENDIENTE")
                .recursoId(prestamoId)
                .build();
        return mapToResponse(repository.save(n));
    }

    @Override
    public List<NotificacionResponseDTO> listarNotificaciones() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<NotificacionResponseDTO> listarPorUsuario(String email) {
        return repository.findByEmailUsuario(email).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<NotificacionResponseDTO> listarPorTipo(String tipo) {
        return repository.findByTipo(tipo).stream().map(this::mapToResponse).toList();
    }

    @Override
    public NotificacionResponseDTO buscarPorId(Long id) {
        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada"));
        return mapToResponse(n);
    }

    @Override
    public NotificacionResponseDTO marcarEnviada(Long id) {
        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada"));
        n.setEstado("ENVIADA");
        log.info("Notificacion id={} marcada como ENVIADA", id);
        return mapToResponse(repository.save(n));
    }

    @Override
    public void eliminarNotificacion(Long id) {
        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada"));
        repository.delete(n);
        log.info("Notificacion eliminada id={}", id);
    }

    private NotificacionResponseDTO mapToResponse(Notificacion n) {
        return NotificacionResponseDTO.builder()
                .id(n.getId()).emailUsuario(n.getEmailUsuario()).mensaje(n.getMensaje())
                .tipo(n.getTipo()).fechaEnvio(n.getFechaEnvio()).estado(n.getEstado())
                .recursoId(n.getRecursoId()).build();
    }
}
