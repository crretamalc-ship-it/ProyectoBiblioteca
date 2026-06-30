package com.biblioteca.msnotificaciones.controller;

import com.biblioteca.msnotificaciones.dto.NotificacionRequestDTO;
import com.biblioteca.msnotificaciones.dto.NotificacionResponseDTO;
import com.biblioteca.msnotificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notificaciones", description = "Operaciones para crear, consultar, enviar y eliminar notificaciones")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "204", description = "Recurso eliminado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService service;

    @Operation(summary = "Crear notificacion", description = "Crea una notificacion para un usuario")
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la notificacion a registrar", required = true)
            @Valid @RequestBody NotificacionRequestDTO dto) {
        log.info("POST /api/notificaciones");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearNotificacion(dto));
    }

    /** Genera una notificacion automatica de vencimiento para un prestamo. */
    @Operation(summary = "Notificar vencimiento", description = "Genera una notificacion automatica por vencimiento de prestamo")
    @PostMapping("/vencimiento/{prestamoId}")
    public ResponseEntity<NotificacionResponseDTO> notificarVencimiento(@Parameter(description = "ID del prestamo", required = true) @PathVariable Long prestamoId) {
        log.info("POST /api/notificaciones/vencimiento/{}", prestamoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.notificarVencimiento(prestamoId));
    }

    @Operation(summary = "Listar notificaciones", description = "Lista todas las notificaciones")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de notificaciones",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = NotificacionResponseDTO.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":12,\"emailUsuario\":\"ana.perez@biblioteca.cl\",\"mensaje\":\"Tu prestamo vence pronto\",\"tipo\":\"VENCIMIENTO_PROXIMO\",\"estado\":\"PENDIENTE\",\"recursoId\":15}]"
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarNotificaciones());
    }

    @Operation(summary = "Buscar notificacion por ID", description = "Obtiene una notificacion por su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarPorId(@Parameter(description = "ID de la notificacion", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Listar notificaciones por usuario", description = "Lista notificaciones asociadas al correo de un usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarPorUsuario(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @Operation(summary = "Marcar notificacion enviada", description = "Marca una notificacion como enviada")
    @PutMapping("/{id}/enviar")
    public ResponseEntity<NotificacionResponseDTO> marcarEnviada(@Parameter(description = "ID de la notificacion", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(service.marcarEnviada(id));
    }

    @Operation(summary = "Eliminar notificacion", description = "Elimina una notificacion por su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la notificacion", required = true) @PathVariable Long id) {
        service.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
