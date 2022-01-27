package com.lutheroaks.tacoswebsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    
    @GetMapping("")
	public String viewIndex() {
		return "Hello World!";
	}
}
