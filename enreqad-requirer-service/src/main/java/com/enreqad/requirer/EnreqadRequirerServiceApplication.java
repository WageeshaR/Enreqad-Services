package com.enreqad.requirer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnreqadRequirerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnreqadRequirerServiceApplication.class, args);
	}

}
