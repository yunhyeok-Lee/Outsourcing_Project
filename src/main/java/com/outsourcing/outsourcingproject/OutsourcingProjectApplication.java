package com.outsourcing.outsourcingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OutsourcingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutsourcingProjectApplication.class, args);
	}

}
