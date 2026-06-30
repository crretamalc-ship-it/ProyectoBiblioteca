package example.ms_favoritos_listas.controller.v2;

import example.ms_favoritos_listas.assemblers.ListaFavoritosModelAssembler;
import example.ms_favoritos_listas.dto.ListaFavoritosRequestDTO;
import example.ms_favoritos_listas.dto.ListaFavoritosResponseDTO;
import example.ms_favoritos_listas.service.ListaFavoritosService;
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
@RequestMapping("/api/v2/listas")
public class ListaFavoritosControllerV2 {

    private final ListaFavoritosService service;
    private final ListaFavoritosModelAssembler assembler;

    public ListaFavoritosControllerV2(ListaFavoritosService service, ListaFavoritosModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ListaFavoritosResponseDTO>> crear(@Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.crear(dto)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ListaFavoritosResponseDTO>>> listar() {
        List<EntityModel<ListaFavoritosResponseDTO>> listas = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(listas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ListaFavoritosResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.buscarPorId(id)));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<CollectionModel<EntityModel<ListaFavoritosResponseDTO>>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<ListaFavoritosResponseDTO>> listas = service.listarPorUsuario(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(listas));
    }

    @GetMapping("/publicas")
    public ResponseEntity<CollectionModel<EntityModel<ListaFavoritosResponseDTO>>> listarPublicas() {
        List<EntityModel<ListaFavoritosResponseDTO>> listas = service.listarPublicas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(listas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ListaFavoritosResponseDTO>> actualizar(@PathVariable Long id,
                                                                             @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.actualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
