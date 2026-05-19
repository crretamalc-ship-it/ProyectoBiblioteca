package com.biblioteca.security_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO para crear un nuevo rol (ej. ROLE_ADMIN, ROLE_SOCIO).
 */
@Data
public class RolCreateDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Pattern(regexp = "^ROLE_[A-Z_]{2,30}$",
             message = "El nombre debe seguir el patron ROLE_XXX (solo mayusculas y guion bajo)")
    private String nombre;
}
