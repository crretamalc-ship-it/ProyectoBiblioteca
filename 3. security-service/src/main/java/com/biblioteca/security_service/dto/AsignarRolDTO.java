package com.biblioteca.security_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * DTO de entrada para asignar un rol a un usuario.
 * Reemplaza el uso directo de la entidad UsuarioRol en el RequestBody.
 */
@Data
public class AsignarRolDTO {

    @NotNull(message = "usuarioId es obligatorio")
    @Positive(message = "usuarioId debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "rolId es obligatorio")
    @Positive(message = "rolId debe ser positivo")
    private Long rolId;
}
