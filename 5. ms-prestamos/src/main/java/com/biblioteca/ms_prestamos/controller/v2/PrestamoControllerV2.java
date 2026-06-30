package com.biblioteca.ms_prestamos.controller.v2;

import com.biblioteca.ms_prestamos.assemblers.PrestamoModelAssembler;
import com.biblioteca.ms_prestamos.dto.PrestamoCreateDTO;
import com.biblioteca.ms_prestamos.dto.PrestamoUpdateDTO;
import com.biblioteca.ms_prestamos.model.Prestamo;
import com.biblioteca.ms_prestamos.service.PrestamoService;
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
@RequestMapping("/api/v2/prestamos")
public class PrestamoControllerV2 {

    private final PrestamoService service;
    private final PrestamoModelAssembler assembler;

    public PrestamoControllerV2(PrestamoService service, PrestamoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping("/registrar")
    public ResponseEntity<EntityModel<Prestamo>> registrar(@Valid @RequestBody PrestamoCreateDTO dto) {
        Prestamo nuevo = service.registrarPrestamo(dto);
        return ResponseEntity.status(201).body(assembler.toModel(nuevo));
    }

    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Prestamo>>> listar() {
        List<EntityModel<Prestamo>> prestamos = service.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(prestamos));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<CollectionModel<EntityModel<Prestamo>>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<Prestamo>> prestamos = service.listarPorEmail(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(prestamos));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<EntityModel<Prestamo>> verUno(@PathVariable Long id) {
        Prestamo prestamo = service.buscarPorId(id);
        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(prestamo));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EntityModel<Prestamo>> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody PrestamoUpdateDTO dto) {
        Prestamo actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
