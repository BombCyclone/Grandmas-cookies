package com.lutheroaks.tacoswebsite.controllers.contact;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.utils.ContactUs;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

	@Autowired
	private ContactUs contactUs;
	
	/**
	 * Sends a contact request email to Tacos Members
	 * @param request
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping("/contact")
	public void contactUs(final HttpServletRequest request, 
			final HttpServletResponse response) throws MessagingException, IOException {
		contactUs.sendContactEmail(request, response);
	}
}
