package example.ms_sugerencias.repository;

import example.ms_sugerencias.model.Sugerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SugerenciaRepository extends JpaRepository<Sugerencia, Long> {
    Optional<Sugerencia> findByIsbn(String isbn);
    List<Sugerencia> findByEmailSocio(String emailSocio);
    List<Sugerencia> findByEstado(String estado);
}
