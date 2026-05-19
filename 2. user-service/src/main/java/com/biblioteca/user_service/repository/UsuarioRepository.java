package com.biblioteca.user_service.repository;

import com.biblioteca.user_service.model.UsuarioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModelo, Long> {
    Optional<UsuarioModelo> findByEmail(String email);
}
