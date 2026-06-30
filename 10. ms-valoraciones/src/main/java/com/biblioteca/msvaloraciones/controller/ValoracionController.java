package com.biblioteca.msvaloraciones.controller;

import com.biblioteca.msvaloraciones.dto.ValoracionRequestDTO;
import com.biblioteca.msvaloraciones.dto.ValoracionResponseDTO;
import com.biblioteca.msvaloraciones.service.ValoracionService;
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
import java.util.Map;

@Tag(name = "Valoraciones", description = "Operaciones para crear, consultar, actualizar y eliminar valoraciones de libros")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "204", description = "Recurso eliminado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/valoraciones")
@RequiredArgsConstructor
@Slf4j
public class ValoracionController {

    private final ValoracionService service;

    @Operation(summary = "Crear valoracion", description = "Registra una valoracion de un libro realizada por un usuario")
    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la valoracion a registrar", required = true)
            @Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearValoracion(dto));
    }

    @Operation(summary = "Listar valoraciones", description = "Lista todas las valoraciones registradas")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de valoraciones",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ValoracionResponseDTO.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":9,\"libroId\":10,\"emailUsuario\":\"ana.perez@biblioteca.cl\",\"puntuacion\":5,\"comentario\":\"Excelente libro, muy recomendado\"}]"
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<ValoracionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarValoraciones());
    }

    @Operation(summary = "Buscar valoracion por ID", description = "Obtiene una valoracion por su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> buscarPorId(@Parameter(description = "ID de la valoracion", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Listar valoraciones por libro", description = "Lista valoraciones asociadas a un libro")
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<ValoracionResponseDTO>> listarPorLibro(@Parameter(description = "ID del libro", required = true) @PathVariable Long libroId) {
        return ResponseEntity.ok(service.listarPorLibro(libroId));
    }

    @Operation(summary = "Listar valoraciones por usuario", description = "Lista valoraciones asociadas al correo de un usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<ValoracionResponseDTO>> listarPorUsuario(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @Operation(summary = "Obtener promedio por libro", description = "Calcula el promedio de valoraciones de un libro")
    @GetMapping("/libro/{libroId}/promedio")
    public ResponseEntity<Map<String, Object>> promedioPorLibro(@Parameter(description = "ID del libro", required = true) @PathVariable Long libroId) {
        return ResponseEntity.ok(service.promedioPorLibro(libroId));
    }

    @Operation(summary = "Actualizar valoracion", description = "Actualiza una valoracion existente")
    @PutMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> actualizar(@Parameter(description = "ID de la valoracion", required = true) @PathVariable Long id,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la valoracion a actualizar", required = true)
                                                            @Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarValoracion(id, dto));
    }

    @Operation(summary = "Eliminar valoracion", description = "Elimina una valoracion por su identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la valoracion", required = true) @PathVariable Long id) {
        service.eliminarValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
