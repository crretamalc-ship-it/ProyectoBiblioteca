package com.biblioteca.ms_inventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.biblioteca.ms_inventario.controller.v2.LibroControllerV2;
import com.biblioteca.ms_inventario.model.Libro;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class LibroModelAssembler implements RepresentationModelAssembler<Libro, EntityModel<Libro>> {

    @Override
    public EntityModel<Libro> toModel(Libro libro) {
        return EntityModel.of(libro,
                linkTo(methodOn(LibroControllerV2.class).verUno(libro.getId())).withSelfRel(),
                linkTo(methodOn(LibroControllerV2.class).verPorIsbn(libro.getIsbn())).withRel("por-isbn"),
                linkTo(methodOn(LibroControllerV2.class).listar()).withRel("libros"));
    }
}
