package com.biblioteca.security_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Schema(description = "Entidad Rol")
@Data
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del rol", example = "1")
    private Long id;
    
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre del rol", example = "ROLE_SOCIO")
    private String nombre; // Ej: ROLE_ADMIN, ROLE_SOCIO, ROLE_BIBLIOTECARIO
}

