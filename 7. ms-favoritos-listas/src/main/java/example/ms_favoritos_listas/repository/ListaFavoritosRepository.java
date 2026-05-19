package example.ms_favoritos_listas.repository;

import example.ms_favoritos_listas.model.ListaFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaFavoritosRepository extends JpaRepository<ListaFavoritos, Long> {
    Optional<ListaFavoritos> findByNombreAndEmailUsuario(String nombre, String emailUsuario);
    List<ListaFavoritos> findByEmailUsuario(String emailUsuario);
    List<ListaFavoritos> findByPublicaTrue();
}
