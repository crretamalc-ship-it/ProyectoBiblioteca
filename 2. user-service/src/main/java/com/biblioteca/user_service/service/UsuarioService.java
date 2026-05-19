package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.UsuarioCreateDTO;
import com.biblioteca.user_service.dto.UsuarioUpdateDTO;
import com.biblioteca.user_service.model.UsuarioModelo;
import com.biblioteca.user_service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository repository;

    public UsuarioModelo crearUsuario(UsuarioCreateDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            log.warn("Intento de crear usuario con email duplicado: {}", dto.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        UsuarioModelo nuevo = new UsuarioModelo();
        nuevo.setNombre(dto.getNombre());
        nuevo.setEmail(dto.getEmail());
        nuevo.setPassword(dto.getPassword());
        nuevo.setTelefono(dto.getTelefono());

        UsuarioModelo guardado = repository.save(nuevo);
        log.info("Usuario creado id={} email={}", guardado.getId(), guardado.getEmail());
        return guardado;
    }

    public UsuarioModelo actualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        UsuarioModelo existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (dto.getNombre() != null) {
            existente.setNombre(dto.getNombre());
        }
        if (dto.getEmail() != null) {
            existente.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            existente.setPassword(dto.getPassword());
        }
        if (dto.getTelefono() != null) {
            existente.setTelefono(dto.getTelefono());
        }

        UsuarioModelo actualizado = repository.save(existente);
        log.info("Usuario actualizado id={}", actualizado.getId());
        return actualizado;
    }

    public void eliminarUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        repository.deleteById(id);
        log.info("Usuario eliminado id={}", id);
    }

    public UsuarioModelo obtenerPorEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public UsuarioModelo obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<UsuarioModelo> listarTodos() {
        return repository.findAll();
    }
}
