package com.lutheroaks.tacoswebsite.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
	private JavaMailSender mailSender;
	
	// This is called when the submit button is clicked on the Contact Us Page
	@PostMapping("/contact")
	public String sendEmail(HttpServletRequest request) throws MessagingException {
		// create the mimeMessage object to be sent
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		// get the requester's name and message
		String message = request.getParameter("message");
		String name = request.getParameter("name");
		// set the email message parameters
        messageHelper.setFrom("tacosemailservice@gmail.com");
        messageHelper.setTo("njande2@ilstu.edu");
        messageHelper.setSubject("TACOS Contact Us Request from " + name);
        messageHelper.setText(message);
		// send the email
        mailSender.send(mimeMessage);
		// return to the homepage
		return "index";
	}
}
