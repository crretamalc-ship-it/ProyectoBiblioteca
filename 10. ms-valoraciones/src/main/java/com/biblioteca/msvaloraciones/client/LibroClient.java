package com.biblioteca.msvaloraciones.client;

import com.biblioteca.msvaloraciones.dto.LibroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-inventario")
public interface LibroClient {
    @GetMapping("/inventario/ver/{id}")
    LibroDto obtenerPorId(@PathVariable("id") Long id);
}
