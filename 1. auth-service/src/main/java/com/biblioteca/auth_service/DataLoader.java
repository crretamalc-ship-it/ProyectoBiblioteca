package com.biblioteca.auth_service;

import com.biblioteca.auth_service.model.LoginRecord;
import com.biblioteca.auth_service.repository.LoginRecordRepository;
import java.time.LocalDateTime;
import java.util.Random;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final LoginRecordRepository repository;

    public DataLoader(LoginRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            LoginRecord record = new LoginRecord();
            record.setEmail(faker.internet().emailAddress());
            record.setLoginTime(LocalDateTime.now().minusDays(random.nextInt(31)));
            record.setExitoso(random.nextInt(3) != 0);
            repository.save(record);
        }
    }
}
