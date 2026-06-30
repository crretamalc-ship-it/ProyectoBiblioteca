package com.biblioteca.user_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.user_service.controller.v2.UsuarioControllerV2;
import com.biblioteca.user_service.model.UsuarioModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioModelo, EntityModel<UsuarioModelo>> {

    @Override
    public EntityModel<UsuarioModelo> toModel(UsuarioModelo usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).buscarPorEmail(usuario.getEmail())).withRel("por-email"),
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withRel("usuarios"));
    }
}
