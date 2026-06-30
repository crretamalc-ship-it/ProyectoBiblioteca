package com.biblioteca.ms_prestamos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.ms_prestamos.controller.v2.PrestamoControllerV2;
import com.biblioteca.ms_prestamos.model.Prestamo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PrestamoModelAssembler implements RepresentationModelAssembler<Prestamo, EntityModel<Prestamo>> {

    @Override
    public EntityModel<Prestamo> toModel(Prestamo prestamo) {
        return EntityModel.of(prestamo,
                linkTo(methodOn(PrestamoControllerV2.class).verUno(prestamo.getId())).withSelfRel(),
                linkTo(methodOn(PrestamoControllerV2.class).listarPorUsuario(prestamo.getEmailUsuario())).withRel("prestamos-usuario"),
                linkTo(methodOn(PrestamoControllerV2.class).listar()).withRel("prestamos"));
    }
}
