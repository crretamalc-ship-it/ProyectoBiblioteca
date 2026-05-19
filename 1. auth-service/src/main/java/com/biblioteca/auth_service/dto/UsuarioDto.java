package com.biblioteca.auth_service.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;
    private String email;
    private String password;
}