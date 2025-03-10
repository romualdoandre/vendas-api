package com.romualdoandre.vendasapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApiApplication {
	@Autowired
	private Environment env;
@GetMapping("/")
	public String getEnv() {
		String ambienteAtual = "nenhum profile";
		if(env.getActiveProfiles().length>0) {
			ambienteAtual=env.getActiveProfiles()[0];
		}
		String appName = env.getProperty("spring.application.name");
		return String.format("App Name: %s. Ambiente: %s", appName, ambienteAtual);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApiApplication.class, args);
	}
}
