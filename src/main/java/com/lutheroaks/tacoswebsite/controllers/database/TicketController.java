package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.controllers.database.helpers.TicketHelpers;
import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.ticket.Ticket;
import com.lutheroaks.tacoswebsite.ticket.TicketRepo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    // for logging information to console
    Logger logger = org.slf4j.LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private ResidentRepo residentRepo;

	private TicketHelpers helper = new TicketHelpers();

	private JavaMailSender mailSender;

    @Autowired
	public TicketController(JavaMailSender mailSender){
		this.mailSender = mailSender;
	}
	
	// This is called when the submit button is clicked on the Submit Ticket Page
	@PostMapping("/submit_ticket")
	public void submitTicket(HttpServletRequest request, HttpServletResponse response) throws MessagingException, IOException {
		/**
		 * There are 3 steps to submitting a ticket
		 * 1) Query the residents table
		 * 		a) if a matching resident is found, use it when creating ticket
		 * 		b) if no match found, create a resident with the information given
		 * 2) Create a new Ticket object and save to the table
		 * 3) Send a confirmation email confirming submission successful
		 */
	//	String message = request.getParameter("message");
	//	System.out.println("Message: " + message);

		try{
			// Get the parameters from the request
			String message = request.getParameter("message");
			System.out.println("Message: " + message);
			String fname = request.getParameter("fname");
			System.out.println("fname" + fname);
			String lname = request.getParameter("lname");
			System.out.println("lname " + lname);
			String fullName = fname + " " + lname;
			System.out.println("fname" + fname);
			String email = request.getParameter("email");
			System.out.println("email " + email);
			int roomNum = Integer.parseInt(request.getParameter("roomNumber"));

			// Step 1 - Search for resident and resolve to variable
			Resident ticketResident = helper.findResident(fname, lname, roomNum, residentRepo);
			residentRepo.save(ticketResident);

			// Step 2 - Create new ticket and set fields
			Ticket ticket = new Ticket();
			ticket.setTicketStatusActive(true);
			ticket.setIssueDesc(message);
			ticket.setTechType("electronics");
			ticket.setTimestamp(Timestamp.from(Instant.now()));
			ticket.setAssignedMembers(new ArrayList<>());
			ticket.setResident(ticketResident);
			ticket.setTags(new ArrayList<>());
			ticket.setComments(new ArrayList<>());
			//save the new ticket
			ticketRepo.save(ticket);

			// Step 3 - send confirmation email
			// add the requester's email to the message if it was provided
			if(email != null){
				message += "\n\nI can be reached at: " + email;
			}
			// pass the parameters for the helper to send the email
			boolean success = helper.sendEmail(fullName, email, message, mailSender);
			// return to homepage if the message sent successfully, reroute to error page if something went wrong
			if(success){
				response.sendRedirect("index");
			}
			else{
				logger.info("Something went wrong while sending the confirmation email");
				response.sendRedirect("error");
			}
		}
		catch(Exception e){
            logger.error("An exception occurred while adding a ticket: ", e);
            response.sendRedirect("error");
		}
	}
}
