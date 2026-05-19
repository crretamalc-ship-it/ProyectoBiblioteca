package com.biblioteca.ms_multas.controller;

import com.biblioteca.ms_multas.dto.MultaAjusteDTO;
import com.biblioteca.ms_multas.model.Multa;
import com.biblioteca.ms_multas.service.MultaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/multas")
public class MultaController {

    private static final Logger log = LoggerFactory.getLogger(MultaController.class);

    @Autowired
    private MultaService service;

    @PostMapping("/calcular/{prestamoId}")
    public ResponseEntity<Object> calcular(@PathVariable Long prestamoId) {
        log.info("POST /multas/calcular/{}", prestamoId);
        try {
            Multa multa = service.calcularMulta(prestamoId);
            return ResponseEntity.status(201).body(multa);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(503).body(ex.getMessage());
        }
    }

    @PostMapping("/pagar/{multaId}")
    public ResponseEntity<Object> pagar(@PathVariable Long multaId) {
        log.info("POST /multas/pagar/{}", multaId);
        try {
            Multa multa = service.pagarMulta(multaId);
            return ResponseEntity.ok(multa);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // Ajuste administrativo de una multa pendiente (PUT).
     
    @PutMapping("/ajustar/{multaId}")
    public ResponseEntity<Object> ajustar(@PathVariable Long multaId,
                                          @Valid @RequestBody MultaAjusteDTO dto) {
        log.info("PUT /multas/ajustar/{}", multaId);
        try {
            Multa multa = service.ajustarMulta(multaId, dto.getDiasRetraso(), dto.getMonto());
            return ResponseEntity.ok(multa);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // Anula la multa (soft delete) usando PUT, conserva trazabilidad.
    @PutMapping("/anular/{multaId}")
    public ResponseEntity<Object> anular(@PathVariable Long multaId) {
        log.info("PUT /multas/anular/{}", multaId);
        try {
            return ResponseEntity.ok(service.anularMulta(multaId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // Eliminacion fisica (DELETE real). Usar con cuidado.
     
    @DeleteMapping("/{multaId}")
    public ResponseEntity<Object> eliminar(@PathVariable Long multaId) {
        log.warn("DELETE /multas/{}", multaId);
        try {
            service.eliminarMulta(multaId);
            return ResponseEntity.ok("Multa eliminada");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<Object> listarPorUsuario(@PathVariable String email) {
        List<Multa> multas = service.listarPorUsuario(email);
        if (multas.isEmpty()) {
            return ResponseEntity.status(404).body("El usuario no tiene multas registradas");
        }
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/pendientes/{email}")
    public ResponseEntity<Map<String, Object>> tienePendientes(@PathVariable String email) {
        boolean pendientes = service.tienePendientes(email);
        long cantidad = service.listarPendientesPorUsuario(email).size();
        return ResponseEntity.ok(Map.of(
                "email", email,
                "tienePendientes", pendientes,
                "cantidad", cantidad
        ));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<Object> verUna(@PathVariable Long id) {
        Multa multa = service.buscarPorId(id);
        if (multa == null) {
            return ResponseEntity.status(404).body("Multa no encontrada");
        }
        return ResponseEntity.ok(multa);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarTodas() {
        List<Multa> multas = service.listarTodas();
        if (multas.isEmpty()) {
            return ResponseEntity.status(404).body("No hay multas registradas");
        }
        return ResponseEntity.ok(multas);
    }
}
