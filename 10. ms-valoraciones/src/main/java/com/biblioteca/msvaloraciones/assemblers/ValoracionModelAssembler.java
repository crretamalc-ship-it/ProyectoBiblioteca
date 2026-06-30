package com.biblioteca.msvaloraciones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.msvaloraciones.controller.v2.ValoracionControllerV2;
import com.biblioteca.msvaloraciones.dto.ValoracionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ValoracionModelAssembler implements RepresentationModelAssembler<ValoracionResponseDTO, EntityModel<ValoracionResponseDTO>> {

    @Override
    public EntityModel<ValoracionResponseDTO> toModel(ValoracionResponseDTO valoracion) {
        return EntityModel.of(valoracion,
                linkTo(methodOn(ValoracionControllerV2.class).buscarPorId(valoracion.getId())).withSelfRel(),
                linkTo(methodOn(ValoracionControllerV2.class).listarPorLibro(valoracion.getLibroId())).withRel("valoraciones-libro"),
                linkTo(methodOn(ValoracionControllerV2.class).listarPorUsuario(valoracion.getEmailUsuario())).withRel("valoraciones-usuario"),
                linkTo(methodOn(ValoracionControllerV2.class).promedioPorLibro(valoracion.getLibroId())).withRel("promedio-libro"),
                linkTo(methodOn(ValoracionControllerV2.class).listar()).withRel("valoraciones"));
    }
}
