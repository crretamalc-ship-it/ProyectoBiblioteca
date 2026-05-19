package example.ms_sugerencias.service.impl;

import example.ms_sugerencias.client.LibroClient;
import example.ms_sugerencias.client.UserClient;
import example.ms_sugerencias.dto.*;
import example.ms_sugerencias.exception.BadRequestException;
import example.ms_sugerencias.exception.ResourceNotFoundException;
import example.ms_sugerencias.model.Sugerencia;
import example.ms_sugerencias.repository.SugerenciaRepository;
import example.ms_sugerencias.service.SugerenciaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SugerenciaServiceImpl implements SugerenciaService {

    private final SugerenciaRepository repository;
    private final UserClient userClient;
    private final LibroClient libroClient;

    private void validarSocio(String email) {
        try {
            UsuarioDto u = userClient.obtenerPorEmail(email);
            if (u == null) throw new ResourceNotFoundException("Socio no encontrado en user-service");
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Socio no encontrado en user-service");
        } catch (FeignException ex) {
            log.error("Feign user-service: {}", ex.getMessage());
            throw new IllegalStateException("Error comunicandose con user-service");
        }
    }

    private void verificarNoExisteEnInventario(String isbn) {
        if (isbn == null || isbn.isBlank()) return;
        try {
            LibroDto libro = libroClient.buscarPorIsbn(isbn);
            if (libro != null) {
                throw new BadRequestException(
                        "El ISBN ya esta en el catalogo de ms-inventario, no se necesita sugerirlo");
            }
        } catch (FeignException.NotFound ex) {
            // OK: no existe en inventario, se puede sugerir
        } catch (FeignException ex) {
            log.warn("No se pudo verificar inventario, se permite la sugerencia. {}", ex.getMessage());
        }
    }

    @Override
    public SugerenciaResponseDTO crear(SugerenciaRequestDTO dto) {
        log.info("Crear sugerencia titulo={} email={}", dto.getTitulo(), dto.getEmailSocio());
        validarSocio(dto.getEmailSocio());
        verificarNoExisteEnInventario(dto.getIsbn());

        repository.findByIsbn(dto.getIsbn()).ifPresent(s -> {
            throw new BadRequestException("Ya existe una sugerencia con ese ISBN");
        });

        Sugerencia s = Sugerencia.builder()
                .titulo(dto.getTitulo()).autor(dto.getAutor()).isbn(dto.getIsbn())
                .comentario(dto.getComentario()).estado("PENDIENTE")
                .fechaSugerencia(LocalDateTime.now()).emailSocio(dto.getEmailSocio())
                .build();
        Sugerencia saved = repository.save(s);
        log.info("Sugerencia creada id={}", saved.getId());
        return mapToDTO(saved);
    }

    @Override
    public List<SugerenciaResponseDTO> listar() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<SugerenciaResponseDTO> listarPorSocio(String email) {
        return repository.findByEmailSocio(email).stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<SugerenciaResponseDTO> listarPorEstado(String estado) {
        return repository.findByEstado(estado).stream().map(this::mapToDTO).toList();
    }

    @Override
    public SugerenciaResponseDTO buscarPorId(Long id) {
        Sugerencia s = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sugerencia no encontrada"));
        return mapToDTO(s);
    }

    @Override
    public SugerenciaResponseDTO actualizar(Long id, SugerenciaRequestDTO dto) {
        Sugerencia s = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sugerencia no encontrada"));
        validarSocio(dto.getEmailSocio());
        s.setTitulo(dto.getTitulo()); s.setAutor(dto.getAutor()); s.setIsbn(dto.getIsbn());
        s.setComentario(dto.getComentario()); s.setEmailSocio(dto.getEmailSocio());
        return mapToDTO(repository.save(s));
    }

    @Override
    public SugerenciaResponseDTO cambiarEstado(Long id, SugerenciaEstadoDTO dto) {
        Sugerencia s = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sugerencia no encontrada"));
        s.setEstado(dto.getEstado());
        log.info("Sugerencia id={} cambia a estado={}", id, dto.getEstado());
        return mapToDTO(repository.save(s));
    }

    @Override
    public void eliminar(Long id) {
        Sugerencia s = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sugerencia no encontrada"));
        repository.delete(s);
        log.info("Sugerencia eliminada id={}", id);
    }

    private SugerenciaResponseDTO mapToDTO(Sugerencia s) {
        return SugerenciaResponseDTO.builder()
                .id(s.getId()).titulo(s.getTitulo()).autor(s.getAutor()).isbn(s.getIsbn())
                .comentario(s.getComentario()).estado(s.getEstado())
                .fechaSugerencia(s.getFechaSugerencia()).emailSocio(s.getEmailSocio()).build();
    }
}
