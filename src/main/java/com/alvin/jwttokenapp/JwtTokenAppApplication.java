package com.alvin.jwttokenapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.alvin.*")
public class JwtTokenAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtTokenAppApplication.class, args);
    }

}
