package com.coco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CocoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoApplication.class, args);
    }

}
