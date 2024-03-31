package com.example.deliveryfee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DeliveryFeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryFeeApplication.class, args);
    }

}
