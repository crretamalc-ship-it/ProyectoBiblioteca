package example.ms_favoritos_listas.controller;

import example.ms_favoritos_listas.dto.ListaFavoritosRequestDTO;
import example.ms_favoritos_listas.dto.ListaFavoritosResponseDTO;
import example.ms_favoritos_listas.service.ListaFavoritosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
@RequiredArgsConstructor
@Slf4j
public class ListaFavoritosController {

    private final ListaFavoritosService service;

    @PostMapping
    public ResponseEntity<ListaFavoritosResponseDTO> crear(@Valid @RequestBody ListaFavoritosRequestDTO dto) {
        log.info("POST /api/listas");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarPorUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.listarPorUsuario(email));
    }

    @GetMapping("/publicas")
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarPublicas() {
        return ResponseEntity.ok(service.listarPublicas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> actualizar(@PathVariable Long id,
                                                                @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
