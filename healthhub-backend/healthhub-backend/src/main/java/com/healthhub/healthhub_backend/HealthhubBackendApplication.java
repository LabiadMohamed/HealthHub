package com.healthhub.healthhub_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class HealthhubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthhubBackendApplication.class, args);
    }
}