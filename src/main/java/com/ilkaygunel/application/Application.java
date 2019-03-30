package com.ilkaygunel.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EntityScan("com.ilkaygunel.entities")
@ComponentScan(basePackages = {"com.ilkaygunel.*"})
@EnableJpaRepositories("com.ilkaygunel.repository")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}