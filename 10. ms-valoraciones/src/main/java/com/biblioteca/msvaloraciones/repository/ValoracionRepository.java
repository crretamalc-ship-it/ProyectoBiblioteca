package com.biblioteca.msvaloraciones.repository;

import com.biblioteca.msvaloraciones.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByLibroId(Long libroId);
    List<Valoracion> findByEmailUsuario(String email);
    Optional<Valoracion> findByLibroIdAndEmailUsuario(Long libroId, String email);
    boolean existsByLibroIdAndEmailUsuario(Long libroId, String email);

    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.libroId = :libroId")
    Double promedioPorLibro(Long libroId);
}
