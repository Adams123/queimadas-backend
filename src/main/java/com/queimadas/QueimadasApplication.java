package com.queimadas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QueimadasApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueimadasApplication.class, args);
    }

}
