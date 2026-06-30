package com.biblioteca.ms_inventario.controller.v2;

import com.biblioteca.ms_inventario.assemblers.LibroModelAssembler;
import com.biblioteca.ms_inventario.dto.LibroCreateDTO;
import com.biblioteca.ms_inventario.dto.LibroUpdateDTO;
import com.biblioteca.ms_inventario.model.Libro;
import com.biblioteca.ms_inventario.service.LibroService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/inventario")
public class LibroControllerV2 {

    private final LibroService service;
    private final LibroModelAssembler assembler;

    public LibroControllerV2(LibroService service, LibroModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Libro>>> listar() {
        List<EntityModel<Libro>> libros = service.listarTodo().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(libros));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<EntityModel<Libro>> verUno(@PathVariable Long id) {
        Libro libro = service.buscarPorId(id);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(libro));
    }

    @PostMapping("/crear")
    public ResponseEntity<EntityModel<Libro>> crear(@Valid @RequestBody LibroCreateDTO dto) {
        Libro creado = service.crear(dto);
        return ResponseEntity.status(201).body(assembler.toModel(creado));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EntityModel<Libro>> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody LibroUpdateDTO dto) {
        Libro actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ver/nombre/{nombre}")
    public ResponseEntity<CollectionModel<EntityModel<Libro>>> verPorNombre(@PathVariable String nombre) {
        List<EntityModel<Libro>> libros = service.buscarPorNombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(libros));
    }

    @GetMapping("/ver/isbn/{isbn}")
    public ResponseEntity<EntityModel<Libro>> verPorIsbn(@PathVariable String isbn) {
        Libro libro = service.buscarPorIsbn(isbn);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(libro));
    }
}
