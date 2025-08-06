package com.authguard.authguard_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthguardUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthguardUserServiceApplication.class, args);
	}

}
