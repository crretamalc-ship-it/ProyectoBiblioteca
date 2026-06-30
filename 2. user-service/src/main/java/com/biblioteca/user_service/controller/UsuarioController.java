package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.UsuarioCreateDTO;
import com.biblioteca.user_service.dto.UsuarioUpdateDTO;
import com.biblioteca.user_service.model.UsuarioModelo;
import com.biblioteca.user_service.service.UsuarioService;
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

@Tag(name = "Usuarios", description = "Operaciones para registrar, consultar, actualizar y eliminar usuarios")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario de biblioteca")
    @PostMapping
    public ResponseEntity<Object> registrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a registrar", required = true)
            @Valid @RequestBody UsuarioCreateDTO dto) {
        log.info("POST /api/users email={}", dto.getEmail());
        UsuarioModelo creado = service.crearUsuario(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Listar usuarios", description = "Lista todos los usuarios registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de usuarios",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UsuarioModelo.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":1,\"nombre\":\"Ana Perez\",\"email\":\"ana.perez@biblioteca.cl\",\"telefono\":\"+56912345678\",\"fechaRegistro\":\"2026-06-29\"}]"
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<UsuarioModelo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar usuario por email", description = "Obtiene un usuario usando su correo")
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> buscarPorEmail(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        UsuarioModelo usuario = service.obtenerPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario usando su identificador")
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> buscarPorId(@Parameter(description = "ID del usuario", required = true) @PathVariable Long id) {
        UsuarioModelo usuario = service.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@Parameter(description = "ID del usuario", required = true) @PathVariable Long id,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a actualizar", required = true)
                                             @Valid @RequestBody UsuarioUpdateDTO dto) {
        log.info("PUT /api/users/{}", id);
        UsuarioModelo actualizado = service.actualizarUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@Parameter(description = "ID del usuario", required = true) @PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);
        service.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
