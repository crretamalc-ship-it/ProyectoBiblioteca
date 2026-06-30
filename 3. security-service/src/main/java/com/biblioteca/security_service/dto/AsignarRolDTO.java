package com.biblioteca.security_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * DTO de entrada para asignar un rol a un usuario.
 * Reemplaza el uso directo de la entidad UsuarioRol en el RequestBody.
 */
@Data
@Schema(description = "Datos para asignar un rol a un usuario")
public class AsignarRolDTO {

    @NotNull(message = "usuarioId es obligatorio")
    @Positive(message = "usuarioId debe ser positivo")
    @Schema(description = "ID del usuario", example = "3")
    private Long usuarioId;

    @NotNull(message = "rolId es obligatorio")
    @Positive(message = "rolId debe ser positivo")
    @Schema(description = "ID del rol", example = "1")
    private Long rolId;
}
