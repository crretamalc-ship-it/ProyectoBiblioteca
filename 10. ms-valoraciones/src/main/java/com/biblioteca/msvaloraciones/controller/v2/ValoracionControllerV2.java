package com.biblioteca.msvaloraciones.controller.v2;

import com.biblioteca.msvaloraciones.assemblers.ValoracionModelAssembler;
import com.biblioteca.msvaloraciones.dto.ValoracionRequestDTO;
import com.biblioteca.msvaloraciones.dto.ValoracionResponseDTO;
import com.biblioteca.msvaloraciones.service.ValoracionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v2/valoraciones")
public class ValoracionControllerV2 {

    private final ValoracionService service;
    private final ValoracionModelAssembler assembler;

    public ValoracionControllerV2(ValoracionService service, ValoracionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> crear(@Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.crearValoracion(dto)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ValoracionResponseDTO>>> listar() {
        List<EntityModel<ValoracionResponseDTO>> valoraciones = service.listarValoraciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(valoraciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.buscarPorId(id)));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<CollectionModel<EntityModel<ValoracionResponseDTO>>> listarPorLibro(@PathVariable Long libroId) {
        List<EntityModel<ValoracionResponseDTO>> valoraciones = service.listarPorLibro(libroId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(valoraciones));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<CollectionModel<EntityModel<ValoracionResponseDTO>>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<ValoracionResponseDTO>> valoraciones = service.listarPorUsuario(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(valoraciones));
    }

    @GetMapping("/libro/{libroId}/promedio")
    public ResponseEntity<EntityModel<Map<String, Object>>> promedioPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(EntityModel.of(service.promedioPorLibro(libroId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ValoracionResponseDTO>> actualizar(@PathVariable Long id,
                                                                         @Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.actualizarValoracion(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
