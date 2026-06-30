package com.biblioteca.security_service.controller.v2;

import com.biblioteca.security_service.assemblers.RolModelAssembler;
import com.biblioteca.security_service.assemblers.UsuarioRolModelAssembler;
import com.biblioteca.security_service.dto.AsignarRolDTO;
import com.biblioteca.security_service.dto.RolCreateDTO;
import com.biblioteca.security_service.model.Rol;
import com.biblioteca.security_service.model.UsuarioRol;
import com.biblioteca.security_service.service.SecurityService;
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
@RequestMapping("/api/v2/security")
public class SecurityControllerV2 {

    private final SecurityService service;
    private final RolModelAssembler rolAssembler;
    private final UsuarioRolModelAssembler usuarioRolAssembler;

    public SecurityControllerV2(SecurityService service,
                                RolModelAssembler rolAssembler,
                                UsuarioRolModelAssembler usuarioRolAssembler) {
        this.service = service;
        this.rolAssembler = rolAssembler;
        this.usuarioRolAssembler = usuarioRolAssembler;
    }

    @PostMapping("/roles")
    public ResponseEntity<EntityModel<Rol>> crearRol(@Valid @RequestBody RolCreateDTO dto) {
        Rol creado = service.crearRol(dto);
        return ResponseEntity.status(201).body(rolAssembler.toModel(creado));
    }

    @GetMapping("/roles")
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> listarRoles() {
        List<EntityModel<Rol>> roles = service.listarRoles().stream()
                .map(rolAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(roles));
    }

    @GetMapping("/roles/buscar/{id}")
    public ResponseEntity<EntityModel<Rol>> verRol(@PathVariable Long id) {
        Rol rol = service.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rolAssembler.toModel(rol));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<EntityModel<Rol>> actualizarRol(@PathVariable Long id,
                                                          @Valid @RequestBody RolCreateDTO dto) {
        Rol actualizado = service.actualizarRol(id, dto);
        return ResponseEntity.ok(rolAssembler.toModel(actualizado));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        service.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<String>> verRolesDeUsuario(@PathVariable Long usuarioId) {
        if (!service.usuarioExiste(usuarioId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CollectionModel.of(service.obtenerRolesDeUsuario(usuarioId)));
    }

    @PostMapping("/asignar")
    public ResponseEntity<EntityModel<UsuarioRol>> asignarRol(@Valid @RequestBody AsignarRolDTO dto) {
        UsuarioRol asignado = service.asignarRol(dto);
        return ResponseEntity.status(201).body(usuarioRolAssembler.toModel(asignado));
    }

    @PutMapping("/asignar/{asignacionId}")
    public ResponseEntity<EntityModel<UsuarioRol>> actualizarAsignacion(@PathVariable Long asignacionId,
                                                                        @Valid @RequestBody AsignarRolDTO dto) {
        UsuarioRol actualizada = service.actualizarAsignacion(asignacionId, dto);
        return ResponseEntity.ok(usuarioRolAssembler.toModel(actualizada));
    }

    @DeleteMapping("/asignar/{usuarioRolId}")
    public ResponseEntity<Void> quitarRol(@PathVariable Long usuarioRolId) {
        service.quitarRol(usuarioRolId);
        return ResponseEntity.noContent().build();
    }
}
