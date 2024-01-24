package com.quiet.onterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnterviewApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnterviewApplication.class, args);
	}

}
