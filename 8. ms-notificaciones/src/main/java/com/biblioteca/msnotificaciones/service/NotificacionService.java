package com.biblioteca.msnotificaciones.service;

import com.biblioteca.msnotificaciones.dto.NotificacionRequestDTO;
import com.biblioteca.msnotificaciones.dto.NotificacionResponseDTO;
import java.util.List;

public interface NotificacionService {
    NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO dto);
    NotificacionResponseDTO notificarVencimiento(Long prestamoId);
    List<NotificacionResponseDTO> listarNotificaciones();
    List<NotificacionResponseDTO> listarPorUsuario(String email);
    List<NotificacionResponseDTO> listarPorTipo(String tipo);
    NotificacionResponseDTO buscarPorId(Long id);
    NotificacionResponseDTO marcarEnviada(Long id);
    void eliminarNotificacion(Long id);
}
