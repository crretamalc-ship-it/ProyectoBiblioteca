package com.biblioteca.ms_multas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.ms_multas.controller.v2.MultaControllerV2;
import com.biblioteca.ms_multas.model.Multa;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MultaModelAssembler implements RepresentationModelAssembler<Multa, EntityModel<Multa>> {

    @Override
    public EntityModel<Multa> toModel(Multa multa) {
        return EntityModel.of(multa,
                linkTo(methodOn(MultaControllerV2.class).verUna(multa.getId())).withSelfRel(),
                linkTo(methodOn(MultaControllerV2.class).listarPorUsuario(multa.getEmailUsuario())).withRel("multas-usuario"),
                linkTo(methodOn(MultaControllerV2.class).tienePendientes(multa.getEmailUsuario())).withRel("pendientes"),
                linkTo(methodOn(MultaControllerV2.class).listarTodas()).withRel("multas"));
    }
}
