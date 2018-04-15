package com.mybank.atm;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Bootstrap class for Spring Boot
 *
 * @author brian.e.reynolds@outlook.com
 */
@SpringBootApplication
public class AtmApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class, args);
    }
}
