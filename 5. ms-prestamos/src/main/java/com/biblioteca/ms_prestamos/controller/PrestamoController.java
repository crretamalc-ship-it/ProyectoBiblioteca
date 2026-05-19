package com.biblioteca.ms_prestamos.controller;

import com.biblioteca.ms_prestamos.dto.PrestamoCreateDTO;
import com.biblioteca.ms_prestamos.dto.PrestamoUpdateDTO;
import com.biblioteca.ms_prestamos.model.Prestamo;
import com.biblioteca.ms_prestamos.service.PrestamoService;
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

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private static final Logger log = LoggerFactory.getLogger(PrestamoController.class);

    @Autowired
    private PrestamoService service;

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@Valid @RequestBody PrestamoCreateDTO dto) {
        log.info("POST /prestamos/registrar email={} libroId={}", dto.getEmailUsuario(), dto.getLibroId());
        Prestamo nuevo = service.registrarPrestamo(dto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<Object> listarPorUsuario(@PathVariable String email) {
        List<Prestamo> prestamos = service.listarPorEmail(email);
        if (prestamos.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("El usuario con correo " + email + " no tiene prestamos registrados");
        }
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<Object> verUno(@PathVariable Long id) {
        Prestamo prestamo = service.buscarPorId(id);
        if (prestamo == null) {
            return ResponseEntity.status(404).body("Prestamo no encontrado");
        }
        return ResponseEntity.ok(prestamo);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody PrestamoUpdateDTO dto) {
        log.info("PUT /prestamos/actualizar/{}", id);
        Prestamo actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id) {
        log.info("DELETE /prestamos/eliminar/{}", id);
        service.eliminar(id);
        return ResponseEntity.ok("Prestamo eliminado");
    }
}
