package com.dogai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DogAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogAiApplication.class, args);
    }

}
