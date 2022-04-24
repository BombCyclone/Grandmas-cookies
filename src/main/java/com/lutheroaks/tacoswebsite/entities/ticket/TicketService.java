package com.lutheroaks.tacoswebsite.entities.ticket;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.entities.tag.Tag;
import com.lutheroaks.tacoswebsite.entities.tag.TagService;
import com.lutheroaks.tacoswebsite.utils.EmailSender;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

	// for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TicketService.class);

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private ResidentRepo residentRepo;

	@Autowired
	private MemberRepo memberRepo;

	@Autowired
	private EmailSender sender;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TagService tagService;

	/**
	 * Searches for a matching Resident in the table
	 * and creates a new resident if no match is found.
	 * @param fname
	 * @param lname
	 * @param roomNum
	 * @return
	 */
    public Resident findResident(final String fname, final String lname, final int roomNum) {
        List<Resident> matchingResidents = residentRepo.findResidentByName(fname, lname);
		Resident ticketResident;
		if(matchingResidents.isEmpty()){
            ticketResident = new Resident();
			// no resident was found, so create new resident
			ticketResident.setFirstName(fname);
			ticketResident.setLastName(lname);
			ticketResident.setRoomNum(roomNum);
			ticketResident.setAssociatedTickets(new ArrayList<>());
			// save the newly created resident
			residentRepo.save(ticketResident);
		} else{
			// set the resident to use to be the match from the query
			ticketResident = matchingResidents.get(0);
		}
        return ticketResident;	
    }

	/**
	 * Saves a new ticket to the table and sends a confirmation email.
	 * Adds a new resident if no matching resident is found from provided information.
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void createTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws MessagingException, IOException {

		try {
			// Get the parameters from the request
			StringBuilder message = new StringBuilder();
			message.append(request.getParameter("message"));
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String fullName = fname + " " + lname;
			int roomNum = Integer.parseInt(request.getParameter("roomNumber"));
			
			// Step 1 - Search for resident and resolve to variable
			Resident ticketResident = findResident(fname, lname, roomNum);

			// Step 2 - Create new ticket and set fields
			Ticket ticket = new Ticket();
			ticket.setTicketStatusActive(true);
			ticket.setIssueDesc(message.toString());
			ticket.setTimestamp(Timestamp.from(Instant.now()));
			ticket.setAssignedMembers(new ArrayList<>());
			ticket.setResident(ticketResident);
			ticket.setAssociatedTags(new ArrayList<>());
			ticket.setComments(new ArrayList<>());
			ticket.setResident(ticketResident);
			// save the new ticket
			ticketRepo.save(ticket);

			// Step 3 - send confirmation email
			String email = request.getParameter("email");
			sendSubmissionEmail(email, message, fullName, response);
	
		} catch (Exception e) {
			logger.error("An exception occurred while adding a ticket: ", e);
			response.sendRedirect("error");
		}
	}

	/**
	 * Sends a ticket submission confirmation email
	 * @param email
	 * @param message
	 * @param fullName
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendSubmissionEmail(final String email, final StringBuilder message, final String fullName,
			final HttpServletResponse response) throws MessagingException, IOException{
		if (email != null && !"".equals(email)) {
			// add the requester's email to the message if it was provided
			message.append("\n\nI can be reached at: " + email);
		}
		String content = fullName +
				", thank you for submitting your issue to ISU Tacos.\n\nA service ticket" +
				" has been created for your request and we will be in contact soon.\n\nTicket message: \n"
				+ message.toString();
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
	}

	/**
	 * Change the fields in an existing Ticket entity based on incoming request
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void updateTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws MessagingException, IOException {
		try {
			// Step 1 - parse parameters
			int ticketId = Integer.parseInt(request.getParameter("ticketId"));
			Ticket ticket = ticketRepo.findTicketById(ticketId);
			Boolean ticketStatus = Boolean.parseBoolean(request.getParameter("ticketstatus"));
			String issueDesc = request.getParameter("issuedesc");
			String[] tags = request.getParameterValues("tags");
			
			// get the tags to apply to the ticket
			List<Tag> appliedTags = tagService.retrieveTags(tags);

			// Step 2 - Update ticket with new fields
			ticket.setAssociatedTags(appliedTags);
			ticket.setTicketStatusActive(ticketStatus);
			ticket.setIssueDesc(issueDesc);
			// save the new ticket
			ticketRepo.save(ticket);
			response.sendRedirect("index");
		} catch (Exception e) {
			logger.error("An exception occurred while updating a ticket: ", e);
			response.sendRedirect("error");
		}
	}

	/**
	 * Assigns a member to handle an existing ticket
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void assignTicket(final HttpServletRequest request, final HttpServletResponse response)
	throws MessagingException, IOException{
		try{
			int ticketId = Integer.parseInt(request.getParameter("ticketId"));
			// get the member IDs of the members we want to assign.
			String[] memberIds = request.getParameterValues("memberId");

			Ticket ticketToUpdate = ticketRepo.findTicketById(ticketId);
			List<Member> assignedMembers = new ArrayList<>();
			if(memberIds != null && memberIds.length < 4){
				for (String idString : memberIds){
					int id = Integer.parseInt(idString);
					Member toAdd = memberRepo.findMemberById(id);
					assignedMembers.add(toAdd);
				}
			}
			// assign and save changes
			ticketToUpdate.setAssignedMembers(assignedMembers);
			ticketRepo.save(ticketToUpdate);
			response.sendRedirect("index");
		} catch (Exception e) {
			logger.error("An exception occurred while adding a ticket: ", e);
			response.sendRedirect("error");
		}
	}

	/**
	 * Deletes a specified ticket
	 * @param request
	 */
	public void removeTicket(final HttpServletRequest request) {
		int ticketNum = Integer.parseInt(request.getParameter("ticketNum"));
		ticketRepo.deleteTicketByTicketNum(ticketNum);
	}
}
