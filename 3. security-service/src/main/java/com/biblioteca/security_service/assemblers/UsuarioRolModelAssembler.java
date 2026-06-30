package com.biblioteca.security_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.security_service.controller.v2.SecurityControllerV2;
import com.biblioteca.security_service.model.UsuarioRol;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRolModelAssembler implements RepresentationModelAssembler<UsuarioRol, EntityModel<UsuarioRol>> {

    @Override
    public EntityModel<UsuarioRol> toModel(UsuarioRol usuarioRol) {
        return EntityModel.of(usuarioRol,
                linkTo(methodOn(SecurityControllerV2.class).verRolesDeUsuario(usuarioRol.getUsuarioId())).withRel("roles-usuario"),
                linkTo(methodOn(SecurityControllerV2.class).listarRoles()).withRel("roles"));
    }
}
