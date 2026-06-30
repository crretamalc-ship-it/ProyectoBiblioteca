package com.biblioteca.msnotificaciones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.msnotificaciones.controller.v2.NotificacionControllerV2;
import com.biblioteca.msnotificaciones.dto.NotificacionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<NotificacionResponseDTO, EntityModel<NotificacionResponseDTO>> {

    @Override
    public EntityModel<NotificacionResponseDTO> toModel(NotificacionResponseDTO notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionControllerV2.class).buscarPorId(notificacion.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).listarPorUsuario(notificacion.getEmailUsuario())).withRel("notificaciones-usuario"),
                linkTo(methodOn(NotificacionControllerV2.class).listarPorTipo(notificacion.getTipo())).withRel("notificaciones-tipo"),
                linkTo(methodOn(NotificacionControllerV2.class).listar()).withRel("notificaciones"));
    }
}
