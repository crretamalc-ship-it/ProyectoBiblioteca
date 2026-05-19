package com.biblioteca.msvaloraciones.controller;

import com.biblioteca.msvaloraciones.dto.ValoracionRequestDTO;
import com.biblioteca.msvaloraciones.dto.ValoracionResponseDTO;
import com.biblioteca.msvaloraciones.service.ValoracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/valoraciones")
@RequiredArgsConstructor
@Slf4j
public class ValoracionController {

    private final ValoracionService service;

    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> crear(@Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearValoracion(dto));
    }

    @GetMapping
    public ResponseEntity<List<ValoracionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarValoraciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<ValoracionResponseDTO>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(service.listarPorLibro(libroId));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<ValoracionResponseDTO>> listarPorUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @GetMapping("/libro/{libroId}/promedio")
    public ResponseEntity<Map<String, Object>> promedioPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(service.promedioPorLibro(libroId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarValoracion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
