package example.ms_sugerencias.controller.v2;

import example.ms_sugerencias.assemblers.SugerenciaModelAssembler;
import example.ms_sugerencias.dto.SugerenciaEstadoDTO;
import example.ms_sugerencias.dto.SugerenciaRequestDTO;
import example.ms_sugerencias.dto.SugerenciaResponseDTO;
import example.ms_sugerencias.service.SugerenciaService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/v2/sugerencias")
public class SugerenciaControllerV2 {

    private final SugerenciaService service;
    private final SugerenciaModelAssembler assembler;

    public SugerenciaControllerV2(SugerenciaService service, SugerenciaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<SugerenciaResponseDTO>> crear(@Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.crear(dto)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SugerenciaResponseDTO>>> listar() {
        List<EntityModel<SugerenciaResponseDTO>> sugerencias = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(sugerencias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SugerenciaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.buscarPorId(id)));
    }

    @GetMapping("/socio/{email}")
    public ResponseEntity<CollectionModel<EntityModel<SugerenciaResponseDTO>>> listarPorSocio(@PathVariable String email) {
        List<EntityModel<SugerenciaResponseDTO>> sugerencias = service.listarPorSocio(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(sugerencias));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<CollectionModel<EntityModel<SugerenciaResponseDTO>>> listarPorEstado(@PathVariable String estado) {
        List<EntityModel<SugerenciaResponseDTO>> sugerencias = service.listarPorEstado(estado).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(sugerencias));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SugerenciaResponseDTO>> actualizar(@PathVariable Long id,
                                                                         @Valid @RequestBody SugerenciaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.actualizar(id, dto)));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<SugerenciaResponseDTO>> cambiarEstado(@PathVariable Long id,
                                                                            @Valid @RequestBody SugerenciaEstadoDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.cambiarEstado(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
