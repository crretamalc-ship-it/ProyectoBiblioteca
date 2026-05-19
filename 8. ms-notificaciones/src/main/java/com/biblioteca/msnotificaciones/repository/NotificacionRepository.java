package com.biblioteca.msnotificaciones.repository;

import com.biblioteca.msnotificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByEmailUsuario(String email);
    List<Notificacion> findByTipo(String tipo);
}
