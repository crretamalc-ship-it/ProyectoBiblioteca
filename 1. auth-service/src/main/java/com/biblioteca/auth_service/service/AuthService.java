package com.biblioteca.auth_service.service;

import com.biblioteca.auth_service.client.SecurityClient;
import com.biblioteca.auth_service.client.UserClient;
import com.biblioteca.auth_service.dto.UsuarioDto;
import com.biblioteca.auth_service.model.LoginRecord;
import com.biblioteca.auth_service.repository.LoginRecordRepository;
import com.biblioteca.auth_service.security.JwtProvider;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private SecurityClient securityClient;

    @Autowired
    private LoginRecordRepository loginRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public String login(String email, String password) {
        LoginRecord record = new LoginRecord();
        record.setEmail(email);
        record.setLoginTime(LocalDateTime.now());

        try {
            // 1. Obtener usuario via Feign -> user-service
            UsuarioDto usuario;
            try {
                usuario = userClient.obtenerPorEmail(email);
            } catch (FeignException.NotFound ex) {
                throw new RuntimeException("Usuario no encontrado en user-service");
            } catch (FeignException ex) {
                log.error("Feign fallo al llamar a user-service: status={}, body={}",
                        ex.status(), ex.contentUTF8(), ex);
                throw new RuntimeException("Error de comunicacion con user-service: " + ex.status());
            }

            if (usuario == null || usuario.getPassword() == null) {
                throw new RuntimeException("user-service devolvio un usuario invalido (sin password)");
            }

            // 2. Validar contrasena
            if (!password.equals(usuario.getPassword())) {
                throw new RuntimeException("Credenciales invalidas");
            }

            // 3. Obtener roles via Feign -> security-service
            List<String> roles;
            try {
                roles = securityClient.obtenerRoles(usuario.getId());
                if (roles == null) {
                    roles = Collections.emptyList();
                }
            } catch (FeignException.NotFound ex) {
                // El usuario existe pero no tiene roles asignados todavia
                log.warn("security-service devolvio 404 para usuarioId={}, asumiendo sin roles",
                        usuario.getId());
                roles = Collections.emptyList();
            } catch (FeignException ex) {
                log.error("Feign fallo al llamar a security-service: status={}, body={}",
                        ex.status(), ex.contentUTF8(), ex);
                throw new RuntimeException("Error de comunicacion con security-service: " + ex.status());
            }

            // 4. Generar JWT
            String token;
            try {
                token = jwtProvider.generarToken(usuario.getEmail(), roles);
            } catch (Exception ex) {
                log.error("Error generando token JWT", ex);
                throw new RuntimeException("Error generando token JWT: " + ex.getMessage());
            }

            record.setExitoso(true);
            try {
                loginRepository.save(record);
            } catch (Exception ex) {
                log.warn("No se pudo registrar el login exitoso en BD: {}", ex.getMessage());
            }

            return token;

        } catch (RuntimeException e) {
            log.error("Login fallido para email={}: {}", email, e.getMessage());
            record.setExitoso(false);
            try {
                loginRepository.save(record);
            } catch (Exception persistEx) {
                log.warn("No se pudo registrar el login fallido en BD: {}", persistEx.getMessage());
            }
            // Propagamos con el mensaje real para que el handler lo devuelva al cliente
            throw e;
        }
    }
}
