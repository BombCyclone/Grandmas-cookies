package com.lutheroaks.tacoswebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TacosWebsiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacosWebsiteApplication.class, args);
	}

}
