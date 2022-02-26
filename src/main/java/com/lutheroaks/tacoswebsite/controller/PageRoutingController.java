package com.lutheroaks.tacoswebsite.controller;


import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.SendEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// This class only contains logic necessary for page routing
@Controller
public class PageRoutingController {

	@GetMapping("/index")
	public String viewIndex() {
		return "index";
	}

	@GetMapping("/ticket-form")
	public String viewTicketForm() {
		return "ticket-form";
	}

	@GetMapping("/active-tickets")
	public String viewActiveTickets() {
		return "active-tickets";
	}

	@GetMapping("/archived-tickets")
	public String viewArchivedTickets() {
		return "archived-tickets";
	}

	@GetMapping("/profile")
	public String viewProfile() {
		return "profile";
	}

	@GetMapping("/faq")
	public String viewFaq() {
		return "faq";
	}

	@GetMapping("/contactus")
	public String viewContactUs() {
		return "contactus";
	}

	@GetMapping("/register")
	public String viewRegistration() {
		return "register";
	}

	@GetMapping("/login")
	public String viewLogin() {
		return "login";
	}	


	@Autowired
	private JavaMailSender javaMailSender;
	@PostMapping("/contact") //is called after someone inputs feedback
	public String sendMail(HttpServletRequest request) {
		String message = request.getParameter("message");
		System.out.println(message);
		String name = request.getParameter("name");
		System.out.println(name);
		SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("tacosemailservice@gmail.com");
        email.setTo("jdkata1@ilstu.edu");
        email.setSubject("TACOS Contact Us Request from " + name);
        email.setText(message);
        javaMailSender.send(email);
        System.out.println("Sent message successfully");
		//javaMailSender.sendEmail("jdkata1@ilstu.edu", "This is the body", "Subject");
		return "index";
	}


}
