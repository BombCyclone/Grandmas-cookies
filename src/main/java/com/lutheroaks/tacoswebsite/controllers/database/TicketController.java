package com.lutheroaks.tacoswebsite.controllers.database;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TicketController {

	private JavaMailSender mailSender;

    @Autowired
	public TicketController(JavaMailSender mailSender){
		this.mailSender = mailSender;
	}
	
	// This is called when the submit button is clicked on the Submit Ticket Page
	@PostMapping("/sumbit-ticket")
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
			messageHelper.setTo("jaskluz@ilstu.edu");
			messageHelper.setSubject("TACOS submit ticket confirmation email");
			messageHelper.setText("Thank you" + fname + " " + lname + "your ticket has been submitted and will be resolved soon.\n" + "Ticket message: \n" + message);
			// send the email
			mailSender.send(mimeMessage);
			// return to the homepage
			return "index";
		}
		catch (Exception e){
			return "error";
		}
	}
}
