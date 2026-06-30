package com.biblioteca.user_service.controller.v2;

import com.biblioteca.user_service.assemblers.UsuarioModelAssembler;
import com.biblioteca.user_service.dto.UsuarioCreateDTO;
import com.biblioteca.user_service.dto.UsuarioUpdateDTO;
import com.biblioteca.user_service.model.UsuarioModelo;
import com.biblioteca.user_service.service.UsuarioService;
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
@RequestMapping("/api/v2/users")
public class UsuarioControllerV2 {

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler;

    public UsuarioControllerV2(UsuarioService service, UsuarioModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioModelo>> registrar(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioModelo creado = service.crearUsuario(dto);
        return ResponseEntity.status(201).body(assembler.toModel(creado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioModelo>>> listar() {
        List<EntityModel<UsuarioModelo>> usuarios = service.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(usuarios));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<UsuarioModelo>> buscarPorEmail(@PathVariable String email) {
        UsuarioModelo usuario = service.obtenerPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<UsuarioModelo>> buscarPorId(@PathVariable Long id) {
        UsuarioModelo usuario = service.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioModelo>> actualizar(@PathVariable Long id,
                                                                 @Valid @RequestBody UsuarioUpdateDTO dto) {
        UsuarioModelo actualizado = service.actualizarUsuario(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
