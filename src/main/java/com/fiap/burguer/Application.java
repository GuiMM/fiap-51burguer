package com.fiap.burguer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fiap.51burguer")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
