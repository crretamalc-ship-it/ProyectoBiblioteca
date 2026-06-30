package com.biblioteca.security_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Schema(description = "Entidad de asignacion de rol a usuario")
@Data
@Entity
@Table(name = "usuarios_roles")
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la asignacion", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID del usuario asignado", example = "3")
    private Long usuarioId; // ID del usuario que viene de user-service

    @ManyToOne
    @JoinColumn(name = "rol_id")
    @Schema(description = "Rol asignado al usuario")
    private Rol rol;
}
