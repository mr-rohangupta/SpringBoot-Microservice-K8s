package com.java.iamauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IamAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IamAuthServiceApplication.class, args);
	}

}
