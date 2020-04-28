package com.enreqad.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnreqadAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnreqadAdminServiceApplication.class, args);
	}

}
