package com.biblioteca.user_service;

import com.biblioteca.user_service.model.UsuarioModelo;
import com.biblioteca.user_service.repository.UsuarioRepository;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository repository;

    public DataLoader(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            UsuarioModelo usuario = new UsuarioModelo();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail("socio" + i + "@biblioteca.cl");
            usuario.setPassword("123456");
            usuario.setTelefono("+569" + (10000000 + random.nextInt(90000000)));
            repository.save(usuario);
        }
    }
}
