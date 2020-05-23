package com.emailalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailAlertApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailAlertApplication.class, args);
    }

}
