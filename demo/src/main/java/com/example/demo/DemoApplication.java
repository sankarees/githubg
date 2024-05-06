package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestContorller
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/name")
	public String getName(@RequestParam(value="name" defaultValue="Baby") String name) {
		return new "Welcome.." + name);
	}
}
