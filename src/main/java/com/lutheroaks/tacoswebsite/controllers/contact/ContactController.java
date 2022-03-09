package com.lutheroaks.tacoswebsite.controllers.contact;

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

	private JavaMailSender mailSender;

    @Autowired
	public ContactController(JavaMailSender mailSender){
		this.mailSender = mailSender;
	}
	
	// This is called when the submit button is clicked on the Contact Us Page
	@PostMapping("/contact")
	public String sendEmail(HttpServletRequest request) throws MessagingException {
		try{
			// create the mimeMessage object to be sent
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			// get the requester's name and message
			String message = request.getParameter("message");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			// add the requester's email to the message if it was provided
			if(email != null){
				message += "\n\nI can be reached at: " + email;
			}
			// set the email message parameters
			messageHelper.setFrom("tacosemailservice@gmail.com");
			messageHelper.setTo("aeheis1@ilstu.edu");
			messageHelper.setSubject("TACOS Contact Us Request from " + fname + ' ' + lname);
			messageHelper.setText(message);
			// send the email
			mailSender.send(mimeMessage);
			// return to the homepage
			return "index";
		}
		catch (Exception e){
			return "contactus";
		}
	}
}
