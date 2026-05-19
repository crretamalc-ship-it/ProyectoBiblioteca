package com.biblioteca.auth_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errores.put(err.getField(), err.getDefaultMessage()));
        log.warn("Validacion fallida: {}", errores);
        return build(HttpStatus.BAD_REQUEST, "Datos de entrada invalidos", errores);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        String message = ex.getMessage() == null ? "Error desconocido" : ex.getMessage();
        HttpStatus status = mapStatus(message);
        log.warn("Login fallido: status={} message={}", status.value(), message);
        return build(status, message, null);
    }

    private HttpStatus mapStatus(String message) {
        String lower = message.toLowerCase();
        if (lower.contains("credenciales")) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (lower.contains("no encontrado")) {
            return HttpStatus.NOT_FOUND;
        }
        if (lower.contains("comunicacion")) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message, Map<String, String> details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message == null ? "" : message);
        if (details != null) {
            body.put("errors", details);
        }
        return ResponseEntity.status(status).body(body);
    }
}
