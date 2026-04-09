package com.ejemplo.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Workshop Spring Boot API",
        version = "1.0",
        description = "API de prueba para el taller de Programación 3",
        contact = @Contact(name = "Cristian", email = "tucorreo@umg.edu.gt")
    )
)
public class SpringbootApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiDemoApplication.class, args);
    }
}