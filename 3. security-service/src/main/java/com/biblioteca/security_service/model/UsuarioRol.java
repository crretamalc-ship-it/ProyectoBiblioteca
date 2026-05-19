package com.biblioteca.security_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios_roles")
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId; // ID del usuario que viene de user-service

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}