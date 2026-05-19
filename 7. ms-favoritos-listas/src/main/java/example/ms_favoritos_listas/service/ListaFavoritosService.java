package example.ms_favoritos_listas.service;

import example.ms_favoritos_listas.dto.ListaFavoritosRequestDTO;
import example.ms_favoritos_listas.dto.ListaFavoritosResponseDTO;
import java.util.List;

public interface ListaFavoritosService {
    ListaFavoritosResponseDTO crear(ListaFavoritosRequestDTO dto);
    List<ListaFavoritosResponseDTO> listar();
    List<ListaFavoritosResponseDTO> listarPorUsuario(String email);
    List<ListaFavoritosResponseDTO> listarPublicas();
    ListaFavoritosResponseDTO buscarPorId(Long id);
    ListaFavoritosResponseDTO actualizar(Long id, ListaFavoritosRequestDTO dto);
    void eliminar(Long id);
}
