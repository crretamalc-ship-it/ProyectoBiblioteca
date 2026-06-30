package com.biblioteca.msnotificaciones.controller.v2;

import com.biblioteca.msnotificaciones.assemblers.NotificacionModelAssembler;
import com.biblioteca.msnotificaciones.dto.NotificacionRequestDTO;
import com.biblioteca.msnotificaciones.dto.NotificacionResponseDTO;
import com.biblioteca.msnotificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    private final NotificacionService service;
    private final NotificacionModelAssembler assembler;

    public NotificacionControllerV2(NotificacionService service, NotificacionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> crear(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.crearNotificacion(dto)));
    }

    @PostMapping("/vencimiento/{prestamoId}")
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> notificarVencimiento(@PathVariable Long prestamoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.notificarVencimiento(prestamoId)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<NotificacionResponseDTO>>> listar() {
        List<EntityModel<NotificacionResponseDTO>> notificaciones = service.listarNotificaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(notificaciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.buscarPorId(id)));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<CollectionModel<EntityModel<NotificacionResponseDTO>>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<NotificacionResponseDTO>> notificaciones = service.listarPorUsuario(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(notificaciones));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<CollectionModel<EntityModel<NotificacionResponseDTO>>> listarPorTipo(@PathVariable String tipo) {
        List<EntityModel<NotificacionResponseDTO>> notificaciones = service.listarPorTipo(tipo).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionControllerV2.class).listarPorTipo(tipo)).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones")));
    }

    @PutMapping("/{id}/enviar")
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> marcarEnviada(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.marcarEnviada(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
