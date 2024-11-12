package com.first.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SeverConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeverConfigServerApplication.class, args);
	}

}
