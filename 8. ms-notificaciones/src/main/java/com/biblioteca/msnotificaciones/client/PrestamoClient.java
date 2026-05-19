package com.biblioteca.msnotificaciones.client;

import com.biblioteca.msnotificaciones.dto.PrestamoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Feign hacia ms-prestamos para detectar prestamos por vencer. */
@FeignClient(name = "ms-prestamos")
public interface PrestamoClient {
    @GetMapping("/prestamos/ver/{id}")
    PrestamoDto obtenerPorId(@PathVariable("id") Long id);
}
