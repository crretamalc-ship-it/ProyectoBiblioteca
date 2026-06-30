package example.ms_sugerencias.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import example.ms_sugerencias.controller.v2.SugerenciaControllerV2;
import example.ms_sugerencias.dto.SugerenciaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SugerenciaModelAssembler implements RepresentationModelAssembler<SugerenciaResponseDTO, EntityModel<SugerenciaResponseDTO>> {

    @Override
    public EntityModel<SugerenciaResponseDTO> toModel(SugerenciaResponseDTO sugerencia) {
        return EntityModel.of(sugerencia,
                linkTo(methodOn(SugerenciaControllerV2.class).buscarPorId(sugerencia.getId())).withSelfRel(),
                linkTo(methodOn(SugerenciaControllerV2.class).listarPorSocio(sugerencia.getEmailSocio())).withRel("sugerencias-socio"),
                linkTo(methodOn(SugerenciaControllerV2.class).listarPorEstado(sugerencia.getEstado())).withRel("sugerencias-estado"),
                linkTo(methodOn(SugerenciaControllerV2.class).listar()).withRel("sugerencias"));
    }
}
