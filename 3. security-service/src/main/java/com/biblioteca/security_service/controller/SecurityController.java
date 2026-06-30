package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.AsignarRolDTO;
import com.biblioteca.security_service.dto.RolCreateDTO;
import com.biblioteca.security_service.model.Rol;
import com.biblioteca.security_service.model.UsuarioRol;
import com.biblioteca.security_service.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Seguridad", description = "Operaciones para administrar roles y asignaciones de usuarios")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private SecurityService service;

    

    @Operation(summary = "Crear rol", description = "Crea un nuevo rol del sistema")
    @PostMapping("/roles")
    public ResponseEntity<Object> crearRol(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del rol a registrar", required = true)
            @Valid @RequestBody RolCreateDTO dto) {
        log.info("POST /api/security/roles nombre={}", dto.getNombre());
        Rol creado = service.crearRol(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Listar roles", description = "Lista todos los roles registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de roles",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Rol.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":1,\"nombre\":\"ROLE_SOCIO\"},{\"id\":2,\"nombre\":\"ROLE_BIBLIOTECARIO\"}]"
                    )
            )
    )
    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(service.listarRoles());
    }

    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol usando su identificador")
    @GetMapping("/roles/buscar/{id}")
    public ResponseEntity<Object> verRol(@Parameter(description = "ID del rol", required = true) @PathVariable Long id) {
        Rol rol = service.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
        return ResponseEntity.ok(rol);
    }

    @Operation(summary = "Actualizar rol", description = "Actualiza el nombre de un rol")
    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> actualizarRol(@Parameter(description = "ID del rol", required = true) @PathVariable Long id,
                                                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del rol a actualizar", required = true)
                                                @Valid @RequestBody RolCreateDTO dto) {
        log.info("PUT /api/security/roles/{} nombre={}", id, dto.getNombre());
        Rol actualizado = service.actualizarRol(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su identificador")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Object> eliminarRol(@Parameter(description = "ID del rol", required = true) @PathVariable Long id) {
        log.info("DELETE /api/security/roles/{}", id);
        service.eliminarRol(id);
        return ResponseEntity.ok("Rol eliminado");
    }


    @Operation(summary = "Listar roles de usuario", description = "Lista los roles asignados a un usuario")
    @GetMapping("/roles/usuario/{usuarioId}")
    public ResponseEntity<Object> verRolesDeUsuario(@Parameter(description = "ID del usuario", required = true) @PathVariable Long usuarioId) {
        List<String> roles = service.obtenerRolesDeUsuario(usuarioId);
        if (roles.isEmpty() && !service.usuarioExiste(usuarioId)) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Asignar rol", description = "Asigna un rol a un usuario existente")
    @PostMapping("/asignar")
    public ResponseEntity<Object> asignarRol(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la asignacion usuario-rol", required = true)
            @Valid @RequestBody AsignarRolDTO dto) {
        log.info("POST /api/security/asignar usuarioId={} rolId={}", dto.getUsuarioId(), dto.getRolId());
        UsuarioRol asignado = service.asignarRol(dto);
        return ResponseEntity.status(201).body(asignado);
    }

    @Operation(summary = "Actualizar asignacion de rol", description = "Actualiza el rol asociado a una asignacion existente")
    @PutMapping("/asignar/{asignacionId}")
    public ResponseEntity<Object> actualizarAsignacion(@Parameter(description = "ID de la asignacion", required = true) @PathVariable Long asignacionId,
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para actualizar la asignacion usuario-rol", required = true)
                                                       @Valid @RequestBody AsignarRolDTO dto) {
        log.info("PUT /api/security/asignar/{}", asignacionId);
        UsuarioRol actualizada = service.actualizarAsignacion(asignacionId, dto);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Quitar rol", description = "Elimina una asignacion de rol")
    @DeleteMapping("/asignar/{usuarioRolId}")
    public ResponseEntity<Object> quitarRol(@Parameter(description = "ID de la asignacion usuario-rol", required = true) @PathVariable Long usuarioRolId) {
        log.info("DELETE /api/security/asignar/{}", usuarioRolId);
        service.quitarRol(usuarioRolId);
        return ResponseEntity.ok("Asignacion eliminada");
    }
}
