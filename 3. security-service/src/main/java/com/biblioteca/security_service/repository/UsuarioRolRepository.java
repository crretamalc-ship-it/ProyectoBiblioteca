package com.biblioteca.security_service.repository;


import com.biblioteca.security_service.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    List<UsuarioRol> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}