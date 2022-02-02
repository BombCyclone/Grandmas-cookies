package com.lutheroaks.tacoswebsite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
