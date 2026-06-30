package com.biblioteca.ms_multas;

import com.biblioteca.ms_multas.model.Multa;
import com.biblioteca.ms_multas.repository.MultaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final MultaRepository repository;

    public DataLoader(MultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 6; i++) {
            boolean pagada = random.nextInt(3) == 0;
            int diasRetraso = random.nextInt(12) + 1;
            Multa multa = new Multa();
            multa.setEmailUsuario("socio" + faker.number().numberBetween(1, 6) + "@biblioteca.cl");
            multa.setPrestamoId((long) i);
            multa.setDiasRetraso(diasRetraso);
            multa.setMonto(BigDecimal.valueOf(diasRetraso * 500L));
            multa.setFechaGeneracion(LocalDate.now().minusDays(random.nextInt(15) + 1));
            multa.setFechaPago(pagada ? LocalDate.now().minusDays(random.nextInt(6)) : null);
            multa.setEstado(pagada ? "PAGADA" : "PENDIENTE");
            repository.save(multa);
        }
    }
}
