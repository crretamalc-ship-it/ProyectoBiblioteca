package com.biblioteca.security_service;

import com.biblioteca.security_service.model.Rol;
import com.biblioteca.security_service.model.UsuarioRol;
import com.biblioteca.security_service.repository.RolRepository;
import com.biblioteca.security_service.repository.UsuarioRolRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    public DataLoader(RolRepository rolRepository, UsuarioRolRepository usuarioRolRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRolRepository = usuarioRolRepository;
    }

    @Override
    public void run(String... args) {
        String[] rolesBase = {"ROLE_ADMIN", "ROLE_SOCIO", "ROLE_BIBLIOTECARIO"};
        for (String nombre : rolesBase) {
            crearRolSiNoExiste(nombre);
        }

        if (usuarioRolRepository.count() > 0) {
            return;
        }

        Rol admin = rolRepository.findByNombre("ROLE_ADMIN").orElseThrow();
        Rol socio = rolRepository.findByNombre("ROLE_SOCIO").orElseThrow();
        List<Rol> roles = List.of(admin, socio, socio, socio, socio);
        for (int i = 0; i < roles.size(); i++) {
            UsuarioRol asignacion = new UsuarioRol();
            asignacion.setUsuarioId((long) i + 1);
            asignacion.setRol(roles.get(i));
            usuarioRolRepository.save(asignacion);
        }
    }

    private Rol crearRolSiNoExiste(String nombre) {
        return rolRepository.findByNombre(nombre).orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            return rolRepository.save(rol);
        });
    }
}
