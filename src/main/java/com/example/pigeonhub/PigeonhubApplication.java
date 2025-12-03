package com.example.pigeonhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PigeonhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigeonhubApplication.class, args);
	}

}
