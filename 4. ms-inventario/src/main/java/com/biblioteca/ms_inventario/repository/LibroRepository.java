package com.biblioteca.ms_inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.ms_inventario.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    // Busca un libro exacto por su ISBN
    Optional<Libro> findByIsbn(String isbn);
}