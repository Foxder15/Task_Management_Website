package com.dev.thanhduy.task_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskBackendApplication.class, args);
        System.out.println("admin account: admin@gmail.com");
        System.out.println("password: admin");
    }

}
