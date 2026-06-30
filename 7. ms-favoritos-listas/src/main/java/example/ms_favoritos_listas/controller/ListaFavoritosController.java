package example.ms_favoritos_listas.controller;

import example.ms_favoritos_listas.dto.ListaFavoritosRequestDTO;
import example.ms_favoritos_listas.dto.ListaFavoritosResponseDTO;
import example.ms_favoritos_listas.service.ListaFavoritosService;
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

@Tag(name = "Favoritos y listas", description = "Operaciones para administrar listas personales y publicas")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "204", description = "Recurso eliminado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/api/listas")
@RequiredArgsConstructor
@Slf4j
public class ListaFavoritosController {

    private final ListaFavoritosService service;

    @Operation(summary = "Crear lista", description = "Crea una lista de favoritos para un usuario")
    @PostMapping
    public ResponseEntity<ListaFavoritosResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la lista de favoritos a registrar", required = true)
            @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        log.info("POST /api/listas");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Listar listas", description = "Lista todas las listas de favoritos")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de listas de favoritos",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ListaFavoritosResponseDTO.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":7,\"nombre\":\"Lecturas de verano\",\"descripcion\":\"Libros recomendados para vacaciones\",\"emailUsuario\":\"ana.perez@biblioteca.cl\",\"publica\":true}]"
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar lista por ID", description = "Obtiene una lista de favoritos por su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> buscarPorId(@Parameter(description = "ID de la lista", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Listar listas por usuario", description = "Lista las listas asociadas al correo de un usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarPorUsuario(@Parameter(description = "Correo del usuario", required = true) @PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @Operation(summary = "Listar listas publicas", description = "Lista las listas marcadas como publicas")
    @GetMapping("/publicas")
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarPublicas() {
        return ResponseEntity.ok(service.listarPublicas());
    }

    @Operation(summary = "Actualizar lista", description = "Actualiza una lista de favoritos existente")
    @PutMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> actualizar(@Parameter(description = "ID de la lista", required = true) @PathVariable Long id,
                                                                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la lista de favoritos a actualizar", required = true)
                                                                @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar lista", description = "Elimina una lista de favoritos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la lista", required = true) @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
