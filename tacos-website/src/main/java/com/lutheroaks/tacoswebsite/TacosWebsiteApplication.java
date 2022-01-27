package com.lutheroaks.tacoswebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class TacosWebsiteApplication {

	@RequestMapping("/")
    public String home() {
        return "Hello World!";
    }
	public static void main(String[] args) {
		SpringApplication.run(TacosWebsiteApplication.class, args);
	}

}
