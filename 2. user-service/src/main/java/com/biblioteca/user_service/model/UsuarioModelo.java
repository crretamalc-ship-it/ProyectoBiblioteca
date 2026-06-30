package com.biblioteca.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Schema(description = "Entidad Usuario")
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del usuario", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Ana Perez")
    private String nombre;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electronico del usuario", example = "ana.perez@biblioteca.cl")
    private String email;

    @Column(nullable = false)
    private String password; // Contraseña hasheada

    @Schema(description = "Telefono de contacto del usuario", example = "+56912345678")
    private String telefono;

    @Schema(description = "Fecha de registro del usuario", example = "2026-06-29")
    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();
    }
}
