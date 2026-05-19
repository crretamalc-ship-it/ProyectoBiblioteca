package com.biblioteca.msvaloraciones.service;

import com.biblioteca.msvaloraciones.dto.ValoracionRequestDTO;
import com.biblioteca.msvaloraciones.dto.ValoracionResponseDTO;
import java.util.List;
import java.util.Map;

public interface ValoracionService {
    ValoracionResponseDTO crearValoracion(ValoracionRequestDTO dto);
    List<ValoracionResponseDTO> listarValoraciones();
    List<ValoracionResponseDTO> listarPorLibro(Long libroId);
    List<ValoracionResponseDTO> listarPorUsuario(String email);
    Map<String, Object> promedioPorLibro(Long libroId);
    ValoracionResponseDTO buscarPorId(Long id);
    ValoracionResponseDTO actualizarValoracion(Long id, ValoracionRequestDTO dto);
    void eliminarValoracion(Long id);
}
