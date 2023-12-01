package com.exemple.integration_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntegrationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationClientApplication.class, args);
	}

}
