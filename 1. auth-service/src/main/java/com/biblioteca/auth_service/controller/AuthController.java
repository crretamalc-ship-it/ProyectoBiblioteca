package com.biblioteca.auth_service.controller;

import com.biblioteca.auth_service.dto.LoginDTO;
import com.biblioteca.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Autenticacion", description = "Operaciones de login y generacion de tokens JWT")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
        @ApiResponse(responseCode = "401", description = "Credenciales invalidas"),
        @ApiResponse(responseCode = "503", description = "Servicio dependiente no disponible")
})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Operation(summary = "Iniciar sesion", description = "Valida credenciales enviadas en JSON y devuelve un token JWT")
    @ApiResponse(
            responseCode = "200",
            description = "Token generado correctamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = "{\"token\":\"eyJhbGciOiJIUzI1NiJ9...\"}"
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginJson(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales de acceso del usuario", required = true)
            @Valid @RequestBody LoginDTO dto) {
        log.info("POST /api/auth/login (json) email={}", dto.getEmail());
        String token = authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "Iniciar sesion con formulario", description = "Valida credenciales enviadas como parametros y devuelve un token JWT")
    @PostMapping("/login-form")
    public ResponseEntity<Map<String, Object>> loginForm(
            @Parameter(description = "Correo del usuario", required = true) @RequestParam String email,
            @Parameter(description = "Password del usuario", required = true) @RequestParam String password) {
        log.info("POST /api/auth/login-form email={}", email);
        String token = authService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
