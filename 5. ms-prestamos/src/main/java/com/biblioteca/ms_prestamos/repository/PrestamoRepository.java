package com.biblioteca.ms_prestamos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.ms_prestamos.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Busca la lista de préstamos asociados a un correo
    List<Prestamo> findByEmailUsuario(String emailUsuario);
}