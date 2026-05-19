package com.biblioteca.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato valido")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 60, message = "La password debe tener entre 4 y 60 caracteres")
    private String password;
}
