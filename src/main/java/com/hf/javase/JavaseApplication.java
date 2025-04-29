package com.hf.javase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class JavaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaseApplication.class, args);
    }

}
