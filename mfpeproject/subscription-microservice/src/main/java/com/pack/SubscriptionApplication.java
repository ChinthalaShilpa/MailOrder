package com.pack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SubscriptionApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SubscriptionApplication.class, args);

	}

}
