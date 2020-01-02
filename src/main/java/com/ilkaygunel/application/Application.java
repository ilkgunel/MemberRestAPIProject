package com.ilkaygunel.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//@SpringBootApplication
//@EntityScan("com.ilkaygunel.entities")
//@ComponentScan(basePackages = {"com.ilkaygunel.*"})
//@EnableJpaRepositories("com.ilkaygunel.repository")
//@EnableAspectJAutoProxy
//public class Application extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//}
