package com.pack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DrugMicroserviceApplication {

	public static void main(String[] args) {
       SpringApplication.run(DrugMicroserviceApplication.class, args);
	}

}
