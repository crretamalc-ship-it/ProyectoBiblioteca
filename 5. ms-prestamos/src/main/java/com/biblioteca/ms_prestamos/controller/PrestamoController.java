package com.biblioteca.ms_prestamos.controller;

import com.biblioteca.ms_prestamos.dto.PrestamoCreateDTO;
import com.biblioteca.ms_prestamos.dto.PrestamoUpdateDTO;
import com.biblioteca.ms_prestamos.model.Prestamo;
import com.biblioteca.ms_prestamos.service.PrestamoService;
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

@Tag(name = "Prestamos", description = "Operaciones para registrar, consultar, actualizar y eliminar prestamos")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio dependiente no disponible")
})
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private static final Logger log = LoggerFactory.getLogger(PrestamoController.class);

    @Autowired
    private PrestamoService service;

    @Operation(summary = "Registrar prestamo", description = "Registra un prestamo validando usuario, libro, stock y multas pendientes")
    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del prestamo a registrar", required = true)
            @Valid @RequestBody PrestamoCreateDTO dto) {
        log.info("POST /prestamos/registrar email={} libroId={}", dto.getEmailUsuario(), dto.getLibroId());
        Prestamo nuevo = service.registrarPrestamo(dto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(summary = "Listar prestamos", description = "Lista todos los prestamos registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de prestamos",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Prestamo.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":15,\"emailUsuario\":\"ana.perez@biblioteca.cl\",\"libroId\":10,\"fechaPrestamo\":\"2026-06-29\",\"fechaDevolucion\":\"2026-07-10\",\"estado\":\"ACTIVO\"}]"
                    )
            )
    )
    @GetMapping("/listar")
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Listar prestamos por usuario", description = "Lista prestamos asociados al correo de un usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<Object> listarPorUsuario(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        List<Prestamo> prestamos = service.listarPorEmail(email);
        if (prestamos.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("El usuario con correo " + email + " no tiene prestamos registrados");
        }
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Buscar prestamo por ID", description = "Obtiene un prestamo usando su identificador")
    @GetMapping("/ver/{id}")
    public ResponseEntity<Object> verUno(@Parameter(description = "ID del prestamo", required = true) @PathVariable Long id) {
        Prestamo prestamo = service.buscarPorId(id);
        if (prestamo == null) {
            return ResponseEntity.status(404).body("Prestamo no encontrado");
        }
        return ResponseEntity.ok(prestamo);
    }

    @Operation(summary = "Actualizar prestamo", description = "Actualiza fecha de devolucion o estado de un prestamo")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizar(@Parameter(description = "ID del prestamo", required = true) @PathVariable Long id,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del prestamo a actualizar", required = true)
                                             @Valid @RequestBody PrestamoUpdateDTO dto) {
        log.info("PUT /prestamos/actualizar/{}", id);
        Prestamo actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar prestamo", description = "Elimina un prestamo por su identificador")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminar(@Parameter(description = "ID del prestamo", required = true) @PathVariable Long id) {
        log.info("DELETE /prestamos/eliminar/{}", id);
        service.eliminar(id);
        return ResponseEntity.ok("Prestamo eliminado");
    }
}
