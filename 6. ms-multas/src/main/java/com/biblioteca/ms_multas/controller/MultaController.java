package com.biblioteca.ms_multas.controller;

import com.biblioteca.ms_multas.dto.MultaAjusteDTO;
import com.biblioteca.ms_multas.model.Multa;
import com.biblioteca.ms_multas.service.MultaService;
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
import java.util.Map;

@Tag(name = "Multas", description = "Operaciones para calcular, pagar, ajustar, anular y consultar multas")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio dependiente no disponible")
})
@RestController
@RequestMapping("/multas")
public class MultaController {

    private static final Logger log = LoggerFactory.getLogger(MultaController.class);

    @Autowired
    private MultaService service;

    @Operation(summary = "Calcular multa", description = "Calcula y registra una multa para un prestamo vencido")
    @PostMapping("/calcular/{prestamoId}")
    public ResponseEntity<Object> calcular(@Parameter(description = "ID del prestamo", required = true) @PathVariable Long prestamoId) {
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

    @Operation(summary = "Pagar multa", description = "Marca una multa pendiente como pagada")
    @PostMapping("/pagar/{multaId}")
    public ResponseEntity<Object> pagar(@Parameter(description = "ID de la multa", required = true) @PathVariable Long multaId) {
        log.info("POST /multas/pagar/{}", multaId);
        try {
            Multa multa = service.pagarMulta(multaId);
            return ResponseEntity.ok(multa);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // Ajuste administrativo de una multa pendiente (PUT).
     
    @Operation(summary = "Ajustar multa", description = "Actualiza dias de retraso o monto de una multa pendiente")
    @PutMapping("/ajustar/{multaId}")
    public ResponseEntity<Object> ajustar(@Parameter(description = "ID de la multa", required = true) @PathVariable Long multaId,
                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para ajustar dias de retraso o monto de la multa", required = true)
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
    @Operation(summary = "Anular multa", description = "Anula una multa conservando trazabilidad")
    @PutMapping("/anular/{multaId}")
    public ResponseEntity<Object> anular(@Parameter(description = "ID de la multa", required = true) @PathVariable Long multaId) {
        log.info("PUT /multas/anular/{}", multaId);
        try {
            return ResponseEntity.ok(service.anularMulta(multaId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // Eliminacion fisica (DELETE real). Usar con cuidado.
     
    @Operation(summary = "Eliminar multa", description = "Elimina fisicamente una multa")
    @DeleteMapping("/{multaId}")
    public ResponseEntity<Object> eliminar(@Parameter(description = "ID de la multa", required = true) @PathVariable Long multaId) {
        log.warn("DELETE /multas/{}", multaId);
        try {
            service.eliminarMulta(multaId);
            return ResponseEntity.ok("Multa eliminada");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @Operation(summary = "Listar multas por usuario", description = "Lista multas asociadas al correo de un usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<Object> listarPorUsuario(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        List<Multa> multas = service.listarPorUsuario(email);
        if (multas.isEmpty()) {
            return ResponseEntity.status(404).body("El usuario no tiene multas registradas");
        }
        return ResponseEntity.ok(multas);
    }

    @Operation(summary = "Consultar multas pendientes", description = "Indica si un usuario tiene multas pendientes y cuantas son")
    @ApiResponse(
            responseCode = "200",
            description = "Resumen de multas pendientes",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "{\"email\":\"ana.perez@biblioteca.cl\",\"tienePendientes\":true,\"cantidad\":2}"
                    )
            )
    )
    @GetMapping("/pendientes/{email}")
    public ResponseEntity<Map<String, Object>> tienePendientes(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        boolean pendientes = service.tienePendientes(email);
        long cantidad = service.listarPendientesPorUsuario(email).size();
        return ResponseEntity.ok(Map.of(
                "email", email,
                "tienePendientes", pendientes,
                "cantidad", cantidad
        ));
    }

    @Operation(summary = "Buscar multa por ID", description = "Obtiene una multa usando su identificador")
    @GetMapping("/ver/{id}")
    public ResponseEntity<Object> verUna(@Parameter(description = "ID de la multa", required = true) @PathVariable Long id) {
        Multa multa = service.buscarPorId(id);
        if (multa == null) {
            return ResponseEntity.status(404).body("Multa no encontrada");
        }
        return ResponseEntity.ok(multa);
    }

    @Operation(summary = "Listar multas", description = "Lista todas las multas registradas")
    @GetMapping("/listar")
    public ResponseEntity<Object> listarTodas() {
        List<Multa> multas = service.listarTodas();
        if (multas.isEmpty()) {
            return ResponseEntity.status(404).body("No hay multas registradas");
        }
        return ResponseEntity.ok(multas);
    }
}
