package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.AsignarRolDTO;
import com.biblioteca.security_service.dto.RolCreateDTO;
import com.biblioteca.security_service.model.Rol;
import com.biblioteca.security_service.model.UsuarioRol;
import com.biblioteca.security_service.service.SecurityService;
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
@RequestMapping("/api/security")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private SecurityService service;

    

    @PostMapping("/roles")
    public ResponseEntity<Object> crearRol(@Valid @RequestBody RolCreateDTO dto) {
        log.info("POST /api/security/roles nombre={}", dto.getNombre());
        Rol creado = service.crearRol(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(service.listarRoles());
    }

    @GetMapping("/roles/buscar/{id}")
    public ResponseEntity<Object> verRol(@PathVariable Long id) {
        Rol rol = service.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> actualizarRol(@PathVariable Long id,
                                                @Valid @RequestBody RolCreateDTO dto) {
        log.info("PUT /api/security/roles/{} nombre={}", id, dto.getNombre());
        Rol actualizado = service.actualizarRol(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Object> eliminarRol(@PathVariable Long id) {
        log.info("DELETE /api/security/roles/{}", id);
        service.eliminarRol(id);
        return ResponseEntity.ok("Rol eliminado");
    }


    @GetMapping("/roles/usuario/{usuarioId}")
    public ResponseEntity<Object> verRolesDeUsuario(@PathVariable Long usuarioId) {
        List<String> roles = service.obtenerRolesDeUsuario(usuarioId);
        if (roles.isEmpty() && !service.usuarioExiste(usuarioId)) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/asignar")
    public ResponseEntity<Object> asignarRol(@Valid @RequestBody AsignarRolDTO dto) {
        log.info("POST /api/security/asignar usuarioId={} rolId={}", dto.getUsuarioId(), dto.getRolId());
        UsuarioRol asignado = service.asignarRol(dto);
        return ResponseEntity.status(201).body(asignado);
    }

    @PutMapping("/asignar/{asignacionId}")
    public ResponseEntity<Object> actualizarAsignacion(@PathVariable Long asignacionId,
                                                       @Valid @RequestBody AsignarRolDTO dto) {
        log.info("PUT /api/security/asignar/{}", asignacionId);
        UsuarioRol actualizada = service.actualizarAsignacion(asignacionId, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/asignar/{usuarioRolId}")
    public ResponseEntity<Object> quitarRol(@PathVariable Long usuarioRolId) {
        log.info("DELETE /api/security/asignar/{}", usuarioRolId);
        service.quitarRol(usuarioRolId);
        return ResponseEntity.ok("Asignacion eliminada");
    }
}
