package com.yj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run 메서드를 통해 내장 WAS 실행
        SpringApplication.run(Application.class, args);
    }
}
