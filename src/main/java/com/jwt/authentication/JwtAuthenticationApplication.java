package com.jwt.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtAuthenticationApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(JwtAuthenticationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.logger.info("Project started !!");
	}
}
