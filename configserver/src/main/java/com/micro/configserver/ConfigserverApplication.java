package com.micro.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {
	
	public static void main(String[] args) {

		String mode=System.getenv("CONFIG_MODE");
		SpringApplication app=new SpringApplication(ConfigserverApplication.class);
		
	if("native".equalsIgnoreCase(mode)) {
		app.setAdditionalProfiles("native");
	}
	else {
		app.setAdditionalProfiles("git");
	}
	app.run(args);
		
	}

}
