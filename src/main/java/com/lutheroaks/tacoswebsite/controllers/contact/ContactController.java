package com.lutheroaks.tacoswebsite.controllers.contact;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.helper_utils.EmailSender;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

	// for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailSender sender;
	
	/**
	 * Sends a contact request email to Tacos Members
	 * @param request
	 * @return
	 * @throws MessagingException
	 */
	@PostMapping("/contact")
	public String sendContactEmail(final HttpServletRequest request) throws MessagingException {
		try{
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
			String subject = "TACOS Contact Us Request from " + fname + " " + lname;
			// send the email
			boolean success = sender.sendEmail(subject, email, message, mailSender);
			// return to the homepage
			if(success){
				return "index";
			}else{
				return "error";
			}
		} catch (MessagingException e){
			logger.error("An exception occurred while parsing the message: ", e);
			return "error";
		}
	}
}
