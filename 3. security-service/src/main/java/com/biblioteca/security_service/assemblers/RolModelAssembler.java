package com.biblioteca.security_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.security_service.controller.v2.SecurityControllerV2;
import com.biblioteca.security_service.model.Rol;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {

    @Override
    public EntityModel<Rol> toModel(Rol rol) {
        return EntityModel.of(rol,
                linkTo(methodOn(SecurityControllerV2.class).verRol(rol.getId())).withSelfRel(),
                linkTo(methodOn(SecurityControllerV2.class).listarRoles()).withRel("roles"));
    }
}
