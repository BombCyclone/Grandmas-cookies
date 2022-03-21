package com.lutheroaks.tacoswebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = { SecurityAutoConfiguration.class })
public class TacosWebsiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacosWebsiteApplication.class, args);
	}

}
