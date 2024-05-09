package com.githubg.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Github Repository API", version = "1.0", description = "Github Automation API performs operations of create PR, merge PR and etc."))
public class GithubgApplication {
	private static final Logger log = LoggerFactory.getLogger(GithubgApplication.class);

	public static void main(String[] args) {
		log.info("Starting ", GithubgApplication.class);
		try {
			SpringApplication.run(GithubgApplication.class, args);
			log.info("Started ", GithubgApplication.class);
		} catch (Exception e) {
			log.error("Exception occurred. Unable to load ", GithubgApplication.class);
			e.printStackTrace();
		}
	}
}