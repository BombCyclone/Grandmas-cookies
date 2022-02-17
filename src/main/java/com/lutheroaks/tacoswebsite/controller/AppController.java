package com.lutheroaks.tacoswebsite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping("")
	public String viewIndex() {
		return "index";
	}

	@GetMapping("/request")
	public String viewForm() {
		return "request";
	}

	@GetMapping("/testurl")
	public String viewTest() {
		return "test";
	}

}
