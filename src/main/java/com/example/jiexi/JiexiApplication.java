package com.example.jiexi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@MapperScan("com.example.jiexi.mapper")
public class JiexiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiexiApplication.class, args);
    }

}
