package com.biblioteca.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtProvider {

    // Nota: En producción, esta clave debe estar en application.yml o variables de entorno
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Tiempo de expiración: 24 horas (en milisegundos)
    private final long expirationTime = 86400000;

    /**
     * Genera un token JWT con el email del usuario y sus roles.
     */
    public String generarToken(String email, List<String> roles) {
        Map<String, Object> claims = Map.of("roles", roles);
        
        return Jwts.builder()
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Valida si el token es estructuralmente correcto y no ha expirado.
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrae el email (subject) del token.
     */
    public String obtenerEmailDeToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}