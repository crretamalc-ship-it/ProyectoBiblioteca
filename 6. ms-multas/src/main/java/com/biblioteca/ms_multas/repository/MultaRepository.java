package com.biblioteca.ms_multas.repository;

import com.biblioteca.ms_multas.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultaRepository extends JpaRepository<Multa, Long> {

    List<Multa> findByEmailUsuario(String emailUsuario);

    List<Multa> findByEmailUsuarioAndEstado(String emailUsuario, String estado);

    boolean existsByEmailUsuarioAndEstado(String emailUsuario, String estado);

    boolean existsByPrestamoId(Long prestamoId);
}
