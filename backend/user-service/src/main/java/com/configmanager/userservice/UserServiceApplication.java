package com.configmanager.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.configmanager.userservice",
        "com.configmanager.usermodule"
})
@EntityScan(basePackages = {
        "com.configmanager.usermodule.model"
})
@EnableJpaRepositories(basePackages = {
        "com.configmanager.usermodule.repository"
})
@OpenAPIDefinition(info = @Info(title = "User Service API", version = "1.0", description = "User Authentication Service"))
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}