package com.configmanager.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.configmanager.configservice",
        "com.configmanager.usermodule"
})
@EntityScan(basePackages = {
        "com.configmanager.configservice.model",
        "com.configmanager.usermodule.model"
})
@EnableJpaRepositories(basePackages = {
        "com.configmanager.configservice.repository",
        "com.configmanager.usermodule.repository"
})
@OpenAPIDefinition(info = @Info(title = "Configuration Service API", version = "1.0", description = "Configuration Management Service"))
public class ConfigServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}