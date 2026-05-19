package com.biblioteca.msvaloraciones.service;

import com.biblioteca.msvaloraciones.client.LibroClient;
import com.biblioteca.msvaloraciones.client.UserClient;
import com.biblioteca.msvaloraciones.dto.*;
import com.biblioteca.msvaloraciones.entity.Valoracion;
import com.biblioteca.msvaloraciones.exception.ResourceNotFoundException;
import com.biblioteca.msvaloraciones.repository.ValoracionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValoracionServiceImpl implements ValoracionService {

    private final ValoracionRepository repository;
    private final UserClient userClient;
    private final LibroClient libroClient;

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

    private void validarLibro(Long libroId) {
        try {
            LibroDto l = libroClient.obtenerPorId(libroId);
            if (l == null) throw new ResourceNotFoundException("Libro no encontrado en ms-inventario");
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Libro no encontrado en ms-inventario");
        } catch (FeignException ex) {
            log.error("Feign ms-inventario: {}", ex.getMessage());
            throw new IllegalStateException("Error comunicandose con ms-inventario");
        }
    }

    @Override
    public ValoracionResponseDTO crearValoracion(ValoracionRequestDTO dto) {
        log.info("Crear valoracion libroId={} email={}", dto.getLibroId(), dto.getEmailUsuario());
        validarUsuario(dto.getEmailUsuario());
        validarLibro(dto.getLibroId());

        if (repository.existsByLibroIdAndEmailUsuario(dto.getLibroId(), dto.getEmailUsuario())) {
            throw new IllegalArgumentException("Ya valoraste este libro. Edita tu valoracion existente.");
        }

        Valoracion v = Valoracion.builder()
                .libroId(dto.getLibroId()).emailUsuario(dto.getEmailUsuario())
                .puntuacion(dto.getPuntuacion()).comentario(dto.getComentario())
                .fechaCreacion(LocalDateTime.now()).build();
        Valoracion saved = repository.save(v);
        log.info("Valoracion creada id={}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    public List<ValoracionResponseDTO> listarValoraciones() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ValoracionResponseDTO> listarPorLibro(Long libroId) {
        return repository.findByLibroId(libroId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ValoracionResponseDTO> listarPorUsuario(String email) {
        return repository.findByEmailUsuario(email).stream().map(this::mapToResponse).toList();
    }

    @Override
    public Map<String, Object> promedioPorLibro(Long libroId) {
        Double promedio = repository.promedioPorLibro(libroId);
        long cantidad = repository.findByLibroId(libroId).size();
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("libroId", libroId);
        result.put("promedio", promedio == null ? 0.0 : promedio);
        result.put("cantidad", cantidad);
        return result;
    }

    @Override
    public ValoracionResponseDTO buscarPorId(Long id) {
        Valoracion v = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion no encontrada"));
        return mapToResponse(v);
    }

    @Override
    public ValoracionResponseDTO actualizarValoracion(Long id, ValoracionRequestDTO dto) {
        Valoracion v = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion no encontrada"));
        validarUsuario(dto.getEmailUsuario());
        validarLibro(dto.getLibroId());
        v.setLibroId(dto.getLibroId());
        v.setEmailUsuario(dto.getEmailUsuario());
        v.setPuntuacion(dto.getPuntuacion());
        v.setComentario(dto.getComentario());
        log.info("Valoracion actualizada id={}", id);
        return mapToResponse(repository.save(v));
    }

    @Override
    public void eliminarValoracion(Long id) {
        Valoracion v = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valoracion no encontrada"));
        repository.delete(v);
        log.info("Valoracion eliminada id={}", id);
    }

    private ValoracionResponseDTO mapToResponse(Valoracion v) {
        return ValoracionResponseDTO.builder()
                .id(v.getId()).libroId(v.getLibroId()).emailUsuario(v.getEmailUsuario())
                .puntuacion(v.getPuntuacion()).comentario(v.getComentario())
                .fechaCreacion(v.getFechaCreacion()).build();
    }
}
