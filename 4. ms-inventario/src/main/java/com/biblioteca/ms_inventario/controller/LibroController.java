package com.biblioteca.ms_inventario.controller;

import com.biblioteca.ms_inventario.dto.LibroCreateDTO;
import com.biblioteca.ms_inventario.dto.LibroUpdateDTO;
import com.biblioteca.ms_inventario.model.Libro;
import com.biblioteca.ms_inventario.service.LibroService;
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

@Tag(name = "Inventario", description = "Operaciones para administrar libros e inventario")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "201", description = "Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
})
@RestController
@RequestMapping("/inventario")
public class LibroController {

    private static final Logger log = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    private LibroService service;

    @Operation(summary = "Listar libros", description = "Lista todos los libros del inventario")
    @ApiResponse(
            responseCode = "200",
            description = "Listado de libros",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Libro.class)
                    ),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "[{\"id\":10,\"titulo\":\"Cien anios de soledad\",\"autor\":\"Gabriel Garcia Marquez\",\"isbn\":\"9780307474728\",\"editorial\":\"Sudamericana\",\"stock\":5}]"
                    )
            )
    )
    @GetMapping("/listar")
    public ResponseEntity<Object> listar() {
        List<Libro> libros = service.listarTodo();
        if (libros.isEmpty()) {
            return ResponseEntity.status(404).body("No hay libros en el inventario");
        }
        return ResponseEntity.ok(libros);
    }

    @Operation(summary = "Buscar libro por ID", description = "Obtiene un libro usando su identificador")
    @GetMapping("/ver/{id}")
    public ResponseEntity<Object> verUno(@Parameter(description = "ID del libro", required = true) @PathVariable Long id) {
        Libro libro = service.buscarPorId(id);
        if (libro == null) {
            return ResponseEntity.status(404).body("Libro no encontrado");
        }
        return ResponseEntity.ok(libro);
    }

    @Operation(summary = "Crear libro", description = "Registra un nuevo libro en el inventario")
    @PostMapping("/crear")
    public ResponseEntity<Object> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del libro a registrar", required = true)
            @Valid @RequestBody LibroCreateDTO dto) {
        log.info("POST /inventario/crear isbn={}", dto.getIsbn());
        Libro creado = service.crear(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Actualizar libro", description = "Actualiza los datos de un libro existente")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizar(@Parameter(description = "ID del libro", required = true) @PathVariable Long id,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del libro a actualizar", required = true)
                                             @Valid @RequestBody LibroUpdateDTO dto) {
        log.info("PUT /inventario/actualizar/{}", id);
        Libro actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar libro", description = "Elimina un libro por su identificador")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminar(@Parameter(description = "ID del libro", required = true) @PathVariable Long id) {
        log.info("DELETE /inventario/eliminar/{}", id);
        service.eliminar(id);
        return ResponseEntity.ok("Libro eliminado");
    }

    @Operation(summary = "Buscar libros por nombre", description = "Busca libros cuyo titulo coincida con el texto indicado")
    @GetMapping("/ver/nombre/{nombre}")
    public ResponseEntity<Object> verPorNombre(@Parameter(description = "Nombre o parte del titulo del libro", required = true) @PathVariable String nombre) {
        List<Libro> libros = service.buscarPorNombre(nombre);
        if (libros.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron libros con ese nombre");
        }
        return ResponseEntity.ok(libros);
    }

    @Operation(summary = "Buscar libro por ISBN", description = "Obtiene un libro usando su ISBN")
    @GetMapping("/ver/isbn/{isbn}")
    public ResponseEntity<Object> verPorIsbn(@Parameter(description = "ISBN del libro", required = true) @PathVariable String isbn) {
        Libro libro = service.buscarPorIsbn(isbn);
        if (libro == null) {
            return ResponseEntity.status(404).body("Libro con ese ISBN no encontrado");
        }
        return ResponseEntity.ok(libro);
    }
}
