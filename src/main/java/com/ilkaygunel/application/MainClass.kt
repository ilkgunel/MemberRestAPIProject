package com.ilkaygunel.application

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("com.ilkaygunel.entities")
@ComponentScan(basePackages = ["com.ilkaygunel.*"])
@EnableJpaRepositories("com.ilkaygunel.repository")
@EnableAspectJAutoProxy
open class MainClass

fun main(args:Array<String>) {
    SpringApplication.run(MainClass::class.java, *args)
}