package com.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.reviews.controller"
})
public class ElectronicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsApplication.class, args);
    }
}
