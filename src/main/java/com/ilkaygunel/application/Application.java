package com.ilkaygunel.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ilkaygunel.entities")
@ComponentScan(basePackages = { "com.ilkaygunel.restservice" })
@ComponentScan(basePackages = { "com.ilkaygunel.repository" })
@ComponentScan(basePackages = { "com.ilkaygunel.service" })
@EnableAutoConfiguration
@EnableJpaRepositories("com.ilkaygunel.repository")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
