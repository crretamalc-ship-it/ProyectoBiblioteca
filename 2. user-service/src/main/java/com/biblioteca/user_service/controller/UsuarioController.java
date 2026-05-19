package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.UsuarioCreateDTO;
import com.biblioteca.user_service.dto.UsuarioUpdateDTO;
import com.biblioteca.user_service.model.UsuarioModelo;
import com.biblioteca.user_service.service.UsuarioService;
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
@RequestMapping("/api/users")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody UsuarioCreateDTO dto) {
        log.info("POST /api/users email={}", dto.getEmail());
        UsuarioModelo creado = service.crearUsuario(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModelo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> buscarPorEmail(@PathVariable String email) {
        UsuarioModelo usuario = service.obtenerPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        UsuarioModelo usuario = service.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody UsuarioUpdateDTO dto) {
        log.info("PUT /api/users/{}", id);
        UsuarioModelo actualizado = service.actualizarUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);
        service.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
