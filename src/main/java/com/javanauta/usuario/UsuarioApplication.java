package com.javanauta.usuario;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.javanauta")
@EntityScan("com.javanauta")
@EnableJpaRepositories("com.javanauta")
public class UsuarioApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);
	}
}
