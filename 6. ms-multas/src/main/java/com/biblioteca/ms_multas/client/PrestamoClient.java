package com.biblioteca.ms_multas.client;

import com.biblioteca.ms_multas.dto.PrestamoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-prestamos")
public interface PrestamoClient {

    @GetMapping("/prestamos/ver/{id}")
    PrestamoDto obtenerPorId(@PathVariable("id") Long id);
}
