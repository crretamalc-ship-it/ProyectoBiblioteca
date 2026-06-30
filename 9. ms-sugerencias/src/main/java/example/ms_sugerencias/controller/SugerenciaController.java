package example.ms_sugerencias.controller;

import example.ms_sugerencias.dto.*;
import example.ms_sugerencias.service.SugerenciaService;
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

@Tag(name = "Sugerencias", description = "Operaciones para crear, consultar y gestionar sugerencias de libros")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "204", description = "Recurso eliminado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/sugerencias")
@RequiredArgsConstructor
@Slf4j
public class SugerenciaController {

    private final SugerenciaService service;

    @Operation(summary = "Crear sugerencia", description = "Registra una sugerencia de libro realizada por un usuario")
    @PostMapping
    public ResponseEntity<SugerenciaResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la sugerencia de libro a registrar", required = true)
            @Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Listar sugerencias", description = "Lista todas las sugerencias registradas")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de sugerencias",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SugerenciaResponseDTO.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":20,\"titulo\":\"El principito\",\"autor\":\"Antoine de Saint-Exupery\",\"isbn\":\"9780156012195\",\"estado\":\"PENDIENTE\",\"emailSocio\":\"ana.perez@biblioteca.cl\"}]"
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<SugerenciaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar sugerencia por ID", description = "Obtiene una sugerencia por su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<SugerenciaResponseDTO> buscarPorId(@Parameter(description = "ID de la sugerencia", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Listar sugerencias por socio", description = "Lista sugerencias asociadas al correo de un socio")
    @GetMapping("/socio/{email}")
    public ResponseEntity<List<SugerenciaResponseDTO>> listarPorSocio(@Parameter(description = "Correo del socio", required = true) @PathVariable String email) {
        return ResponseEntity.ok(service.listarPorSocio(email));
    }

    @Operation(summary = "Listar sugerencias por estado", description = "Lista sugerencias filtradas por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SugerenciaResponseDTO>> listarPorEstado(@Parameter(description = "Estado de la sugerencia", required = true) @PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @Operation(summary = "Actualizar sugerencia", description = "Actualiza los datos de una sugerencia existente")
    @PutMapping("/{id}")
    public ResponseEntity<SugerenciaResponseDTO> actualizar(@Parameter(description = "ID de la sugerencia", required = true) @PathVariable Long id,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la sugerencia de libro a actualizar", required = true)
                                                            @Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Cambiar estado de sugerencia", description = "Actualiza el estado de revision de una sugerencia")
    @PutMapping("/{id}/estado")
    public ResponseEntity<SugerenciaResponseDTO> cambiarEstado(@Parameter(description = "ID de la sugerencia", required = true) @PathVariable Long id,
                                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Estado nuevo de la sugerencia", required = true)
                                                               @Valid @RequestBody SugerenciaEstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto));
    }

    @Operation(summary = "Eliminar sugerencia", description = "Elimina una sugerencia por su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la sugerencia", required = true) @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
