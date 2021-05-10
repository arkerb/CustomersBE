package com.example.CustmomersBE.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            Customer juku = new Customer(
                    "Juku",
                    "Mees",
                    LocalDate.of(1996, APRIL, 3),
                    "Juss23",
                    "password"
            );
            Customer kati = new Customer(
                    "Kati",
                    "Lahe",
                    LocalDate.of(1989, DECEMBER, 8),
                    "KatiMati",
                    "1234456"
            );
            repository.saveAll(
                    List.of(juku,kati)
            );
        };
    }
}
