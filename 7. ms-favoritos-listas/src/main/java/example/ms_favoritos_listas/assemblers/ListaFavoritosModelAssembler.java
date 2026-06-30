package example.ms_favoritos_listas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import example.ms_favoritos_listas.controller.v2.ListaFavoritosControllerV2;
import example.ms_favoritos_listas.dto.ListaFavoritosResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ListaFavoritosModelAssembler implements RepresentationModelAssembler<ListaFavoritosResponseDTO, EntityModel<ListaFavoritosResponseDTO>> {

    @Override
    public EntityModel<ListaFavoritosResponseDTO> toModel(ListaFavoritosResponseDTO lista) {
        return EntityModel.of(lista,
                linkTo(methodOn(ListaFavoritosControllerV2.class).buscarPorId(lista.getId())).withSelfRel(),
                linkTo(methodOn(ListaFavoritosControllerV2.class).listarPorUsuario(lista.getEmailUsuario())).withRel("listas-usuario"),
                linkTo(methodOn(ListaFavoritosControllerV2.class).listarPublicas()).withRel("publicas"),
                linkTo(methodOn(ListaFavoritosControllerV2.class).listar()).withRel("listas"));
    }
}
