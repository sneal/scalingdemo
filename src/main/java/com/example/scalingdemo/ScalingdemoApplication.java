package com.example.scalingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

public class ScalingdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScalingdemoApplication.class, args);
	}

}
