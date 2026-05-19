package example.ms_sugerencias.controller;

import example.ms_sugerencias.dto.*;
import example.ms_sugerencias.service.SugerenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugerencias")
@RequiredArgsConstructor
@Slf4j
public class SugerenciaController {

    private final SugerenciaService service;

    @PostMapping
    public ResponseEntity<SugerenciaResponseDTO> crear(@Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<SugerenciaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SugerenciaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/socio/{email}")
    public ResponseEntity<List<SugerenciaResponseDTO>> listarPorSocio(@PathVariable String email) {
        return ResponseEntity.ok(service.listarPorSocio(email));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SugerenciaResponseDTO>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SugerenciaResponseDTO> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SugerenciaResponseDTO> cambiarEstado(@PathVariable Long id,
                                                               @Valid @RequestBody SugerenciaEstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
