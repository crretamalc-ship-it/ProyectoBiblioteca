package com.biblioteca.auth_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "Registro de un intento de inicio de sesion")
@Data
@Entity
@Table(name = "login_history")
public class LoginRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del registro de login", example = "1")
    private Long id;
    @Schema(description = "Correo usado para iniciar sesion", example = "socio@biblioteca.cl")
    private String email;
    @Schema(description = "Fecha y hora del intento de login", example = "2026-06-29T14:30:00")
    private LocalDateTime loginTime;
    @Schema(description = "Indica si el login fue exitoso", example = "true")
    private boolean exitoso;
}
