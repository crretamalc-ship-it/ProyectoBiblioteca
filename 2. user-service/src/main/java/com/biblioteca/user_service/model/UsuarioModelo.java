package com.biblioteca.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Contraseña hasheada

    private String telefono;

    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();
    }
}