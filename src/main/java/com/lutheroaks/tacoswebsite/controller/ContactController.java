package com.lutheroaks.tacoswebsite.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
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
