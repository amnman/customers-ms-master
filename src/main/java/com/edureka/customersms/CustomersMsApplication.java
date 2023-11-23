package com.edureka.customersms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CustomersMsApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
		.profiles("DEV")
		.sources(CustomersMsApplication.class)
		.run(args);
	}

}
