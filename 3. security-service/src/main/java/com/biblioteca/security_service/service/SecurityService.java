package com.biblioteca.security_service.service;

import com.biblioteca.security_service.client.UserClient;
import com.biblioteca.security_service.dto.AsignarRolDTO;
import com.biblioteca.security_service.dto.RolCreateDTO;
import com.biblioteca.security_service.model.Rol;
import com.biblioteca.security_service.model.UsuarioRol;
import com.biblioteca.security_service.repository.RolRepository;
import com.biblioteca.security_service.repository.UsuarioRolRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserClient userClient;

    // ----- Roles (CRUD) -----

    public Rol crearRol(RolCreateDTO dto) {
        if (rolRepository.findByNombre(dto.getNombre()).isPresent()) {
            log.warn("Intento de crear rol duplicado nombre={}", dto.getNombre());
            throw new IllegalArgumentException("Ya existe un rol con ese nombre");
        }
        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        Rol creado = rolRepository.save(rol);
        log.info("Rol creado id={} nombre={}", creado.getId(), creado.getNombre());
        return creado;
    }

    // Actualiza el nombre de un rol existente
     
    public Rol actualizarRol(Long id, RolCreateDTO dto) {
        log.info("Actualizando rol id={} nuevoNombre={}", id, dto.getNombre());
        Rol existente = rolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        rolRepository.findByNombre(dto.getNombre()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro rol con ese nombre");
            }
        });

        existente.setNombre(dto.getNombre());
        Rol actualizado = rolRepository.save(existente);
        log.info("Rol actualizado id={} nombre={}", actualizado.getId(), actualizado.getNombre());
        return actualizado;
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new IllegalArgumentException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
        log.info("Rol eliminado id={}", id);
    }

    // ----- Usuario Rol -----

    public List<String> obtenerRolesDeUsuario(Long usuarioId) {
        return usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                .map(ur -> ur.getRol().getNombre())
                .collect(Collectors.toList());
    }

    public boolean usuarioExiste(Long usuarioId) {
        try {
            return userClient.obtenerPorId(usuarioId) != null;
        } catch (FeignException.NotFound ex) {
            log.debug("user-service responde 404 para usuarioId={}", usuarioId);
            return false;
        } catch (FeignException ex) {
            log.error("Error consultando user-service via Feign status={} msg={}",
                    ex.status(), ex.getMessage());
            return false;
        }
    }

    public UsuarioRol asignarRol(AsignarRolDTO dto) {
        log.info("Asignando rolId={} a usuarioId={}", dto.getRolId(), dto.getUsuarioId());
        if (!usuarioExiste(dto.getUsuarioId())) {
            throw new IllegalArgumentException("Usuario no encontrado en user-service");
        }
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuarioId(dto.getUsuarioId());
        usuarioRol.setRol(rol);
        UsuarioRol asignado = usuarioRolRepository.save(usuarioRol);
        log.info("Asignacion creada id={} usuarioId={} rolId={}",
                asignado.getId(), dto.getUsuarioId(), dto.getRolId());
        return asignado;
    }

    //Actualiza el rol asignado a una asignacion existente (PUT).
     
    public UsuarioRol actualizarAsignacion(Long asignacionId, AsignarRolDTO dto) {
        log.info("Actualizando asignacion id={} nuevoRolId={}", asignacionId, dto.getRolId());
        UsuarioRol existente = usuarioRolRepository.findById(asignacionId)
                .orElseThrow(() -> new IllegalArgumentException("Asignacion no encontrada"));

        if (!usuarioExiste(dto.getUsuarioId())) {
            throw new IllegalArgumentException("Usuario no encontrado en user-service");
        }
        Rol nuevoRol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        existente.setUsuarioId(dto.getUsuarioId());
        existente.setRol(nuevoRol);
        UsuarioRol actualizada = usuarioRolRepository.save(existente);
        log.info("Asignacion actualizada id={}", actualizada.getId());
        return actualizada;
    }

    public void quitarRol(Long usuarioRolId) {
        if (!usuarioRolRepository.existsById(usuarioRolId)) {
            throw new IllegalArgumentException("Asignacion de rol no encontrada");
        }
        usuarioRolRepository.deleteById(usuarioRolId);
        log.info("Asignacion eliminada id={}", usuarioRolId);
    }
}
