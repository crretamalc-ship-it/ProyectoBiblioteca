package com.biblioteca.user_service.dto;

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
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 60, message = "La password debe tener entre 4 y 60 caracteres")
    private String password;

    @Pattern(regexp = "^[0-9+\\- ]{6,20}$",
             message = "El telefono solo puede contener numeros, espacios, '+' y '-' (6-20 caracteres)")
    private String telefono;
}
