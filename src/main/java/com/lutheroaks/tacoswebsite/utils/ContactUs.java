package com.lutheroaks.tacoswebsite.utils;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactUs {

    // for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactUs.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailSender sender;
    
    /**
     * Handles the parsing and setting of parameters for a Contact Request Email Submission
     * @param request
     * @param response
     * @throws MessagingException
     * @throws IOException
     */
    public void sendContactEmail(final HttpServletRequest request, 
    final HttpServletResponse response) throws MessagingException, IOException {
        try{
            // get the requester's name and message
            String message = request.getParameter("message");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String email = request.getParameter("email");
            // add the requester's email to the message if it was provided
            if(email != null && !"".equals(email)){
                message += "\n\nI can be reached at: " + email;
            }
            // set the email message parameters
            String subject = "TACOS Contact Us Request from " + fname + " " + lname;
            // send the email
            boolean success = sender.sendEmail(subject, email, message, mailSender);
            // return to the homepage
            if(success){
                response.sendRedirect("index");
            }else{
                response.sendRedirect("error");
            }
        } catch (MessagingException e){
            logger.error("An exception occurred while parsing the message: ", e);
            response.sendRedirect("error");
        }
    }
}
