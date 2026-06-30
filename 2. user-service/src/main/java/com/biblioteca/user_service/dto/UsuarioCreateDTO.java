package com.biblioteca.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de entrada para crear un usuario (POST /api/users).
 * Aplica Bean Validation (JSR 380) para validar los datos antes de tocar la BD.
 */
@Data
@Schema(description = "Datos para crear un usuario")
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Ana Perez")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Schema(description = "Correo electronico del usuario", example = "ana.perez@biblioteca.cl")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 60, message = "La password debe tener entre 4 y 60 caracteres")
    @Schema(description = "Password del usuario", example = "secreto123")
    private String password;

    @Pattern(regexp = "^[0-9+\\- ]{6,20}$",
             message = "El telefono solo puede contener numeros, espacios, '+' y '-' (6-20 caracteres)")
    @Schema(description = "Telefono de contacto", example = "+56912345678")
    private String telefono;
}
