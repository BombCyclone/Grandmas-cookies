package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.helper_utils.EmailSender;
import com.lutheroaks.tacoswebsite.helper_utils.TicketHelper;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;
import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.ticket.Ticket;
import com.lutheroaks.tacoswebsite.ticket.TicketRepo;

import org.hibernate.mapping.Set;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;

@RestController
public class TicketController {

    // for logging information to console
    private Logger logger = org.slf4j.LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private ResidentRepo residentRepo;

	@Autowired
	private MemberRepo memberRepo;

	@Autowired
	private TicketHelper helper;

	@Autowired
	private EmailSender sender;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Saves a new ticket to the table and sends a confirmation email.
	 * Adds a new resident if no matching resident is found from provided information.
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping("/submit_ticket")
	public void submitTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws MessagingException, IOException {
		/**
		 * There are 3 steps to submitting a ticket
		 * 1) Query the residents table
		 * a) if a matching resident is found, use it when creating ticket
		 * b) if no match found, create a resident with the information given
		 * 2) Create a new Ticket object and save to the table
		 * 3) Send a confirmation email confirming submission successful
		 */

		try {
			// Get the parameters from the request
			String message = request.getParameter("message");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String fullName = fname + " " + lname;
			String email = request.getParameter("email");
			int roomNum = Integer.parseInt(request.getParameter("roomNumber"));

			// Step 1 - Search for resident and resolve to variable
			Resident ticketResident = helper.findResident(fname, lname, roomNum);
			// save the returned resident to the repository
			residentRepo.save(ticketResident);

			// Step 2 - Create new ticket and set fields
			Ticket ticket = new Ticket();
			ticket.setTicketStatusActive(true);
			ticket.setIssueDesc(message);
			ticket.setTechType("electronics");
			ticket.setTimestamp(Timestamp.from(Instant.now()));
			ticket.setAssignedMembers(new HashSet<>());
			ticket.setResident(ticketResident);
			ticket.setTags(new ArrayList<>());
			ticket.setComments(new ArrayList<>());
			ticket.setResident(ticketResident);
			// save the new ticket
			ticketRepo.save(ticket);

			// Step 3 - send confirmation email
			if (email != null && !"".equals(email)) {
				// add the requester's email to the message if it was provided
				message += "\n\nI can be reached at: " + email;
			}
			String content = fullName +
					", thank you for contacting ISU Tacos.\n\nYour ticket" +
					" has been submitted and will be in contact soon.\n\nTicket message: \n"
					+ message;
			String subject = "TACOS ticket submission confirmation email";
			// pass the parameters for the helper to send the email
			boolean success = sender.sendEmail(subject, email, content, mailSender);
			// return to homepage if the message sent successfully, reroute to error page if
			// something went wrong
			if (success) {
				response.sendRedirect("index");
			} else {
				response.sendRedirect("error");
			}
		} catch (Exception e) {
			logger.error("An exception occurred while adding a ticket: ", e);
			response.sendRedirect("error");
		}
	}

	/**
	 * Returns a list of all tickets in the table
	 * @return
	 */
	@GetMapping("/tickets")
	public List<Ticket> getTickets() {
		return ticketRepo.findAll();
	}


	@PutMapping("/tickets")
	public void UpdateTicket(final HttpServletRequest request, final HttpServletResponse response) throws IOException, MessagingException
	{

		// step 1: parse new paramaters of ticket. Assume all of the appropriate fields are all provided for now.
		int ticketID = Integer.parseInt(request.getParameter("ticketID"));
		Ticket originalTicket = ticketRepo.findTicketById(ticketID);
		Ticket updatedTicket = originalTicket;
		Boolean ticketStatus = Boolean.parseBoolean(request.getParameter("ticketstatus"));
		String techType = request.getParameter("techtype");
		 /*
		java.util.@NonNull Set<Member> assignedMembers = originalTicket.getAssignedMembers();
		//Easier to assume that the form we get the request from will give us the email, for now. 
		String[] memberEmails = request.getParameterValues("email");

		if (memberEmails.length < 3){ // check if assigning these members would go beyond limit of 3
			for (int i =0; i < memberEmails.length; i++){
				Member tempMember = memberRepo.findMemberByEmail(memberEmails[i]);
				assignedMembers.add(tempMember);
				//TO DO: Need to unassign members who weren't given in the paramters

			}
		}
		else response.sendRedirect("error"); // A ticket cannot have more than 3 assigned members
		*/
		//List<String> tags = Arrays.asList(request.getParameterValues("tags"));

		updatedTicket.setTicketStatusActive(ticketStatus);
		updatedTicket.setTechType(techType);
	//	updatedTicket.setAssignedMembers(assignedMembers);
	//	updatedTicket.setTags(tags);
	//	updatedTicket.setComments(new ArrayList<>());
	/*
		Iterator<Member> newlyAssignedMembers = updatedTicket.getAssignedMembers().iterator();
		while (newlyAssignedMembers.hasNext()){ // need to update members that have been assigned to this ticket
			Member updatedMember = newlyAssignedMembers.next();
			HashSet<Ticket> memberTickets = (HashSet<Ticket>) updatedMember.getAssociatedTickets();
			// hash set doesn't allow duplicates so go ahead and write over whatever it had, and it if its there, it will be overwritten
			memberTickets.add(updatedTicket);
			memberRepo.save(updatedMember);
			// if my understanding of the JpaCRUD system is correct, it should find the matching member objects and
			// overwrite their AssociatedTickets with this new updated versions, without creating a new member
		} */

		ticketRepo.save(updatedTicket);
		response.sendRedirect("active-tickets");
	}


	/**
	 * Deletes a ticket from the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@DeleteMapping("/tickets")
	public void deleteTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
			int ticketNum = Integer.parseInt(request.getParameter("ticketNum"));
			ticketRepo.deleteTicketByTicketNum(ticketNum);
			response.sendRedirect("active-tickets");
	}
}
