package com.biblioteca.msnotificaciones.controller;

import com.biblioteca.msnotificaciones.dto.NotificacionRequestDTO;
import com.biblioteca.msnotificaciones.dto.NotificacionResponseDTO;
import com.biblioteca.msnotificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService service;

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionRequestDTO dto) {
        log.info("POST /api/notificaciones");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearNotificacion(dto));
    }

    /** Genera una notificacion automatica de vencimiento para un prestamo. */
    @PostMapping("/vencimiento/{prestamoId}")
    public ResponseEntity<NotificacionResponseDTO> notificarVencimiento(@PathVariable Long prestamoId) {
        log.info("POST /api/notificaciones/vencimiento/{}", prestamoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.notificarVencimiento(prestamoId));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarNotificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarPorUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @PutMapping("/{id}/enviar")
    public ResponseEntity<NotificacionResponseDTO> marcarEnviada(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarEnviada(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
