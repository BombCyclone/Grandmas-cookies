package com.lutheroaks.tacoswebsite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageRoutingController {
	
	@GetMapping("/index")
	public String viewIndex() {
		return "index";
	}

	@GetMapping("/profile")
	public String viewProfile() {
		return "profile";
	}

	@GetMapping("/contactus")
	public String contactUs() {
		return "contactus";
	}

}
