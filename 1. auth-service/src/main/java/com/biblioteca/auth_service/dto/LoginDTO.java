package com.biblioteca.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Credenciales de acceso")
public class LoginDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato valido")
    @Schema(description = "Correo electronico del usuario", example = "ana.perez@biblioteca.cl")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 60, message = "La password debe tener entre 4 y 60 caracteres")
    @Schema(description = "Password del usuario", example = "secreto123")
    private String password;
}
