package com.ecs.ecs_customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcsCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcsCustomerApplication.class, args);
	}

}
