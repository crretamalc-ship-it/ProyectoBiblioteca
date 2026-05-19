package com.biblioteca.security_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nombre; // Ej: ROLE_ADMIN, ROLE_SOCIO, ROLE_BIBLIOTECARIO
}

