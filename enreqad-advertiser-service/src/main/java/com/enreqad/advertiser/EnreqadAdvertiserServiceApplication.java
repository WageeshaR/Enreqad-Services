package com.enreqad.advertiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnreqadAdvertiserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnreqadAdvertiserServiceApplication.class, args);
	}

}
