package com.lutheroaks.tacoswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping("")
	public String viewIndex() {
		return "../static/index.html";
	}
}
