package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

	@Autowired
	private TicketRepo repository;

	@Autowired
	private TicketService service;

	/**
	 * Saves a new ticket to the table and sends a confirmation email.
	 * Adds a new resident if no matching resident is found from provided information.
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping("/ticket")
	public void addTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws MessagingException, IOException {
		service.createTicket(request, response);
	}

	/**
	 * Modifies an existing ticket's parameters
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping("/ticket-update")
	public void updateTicket(final HttpServletRequest request, final HttpServletResponse response)
			throws MessagingException, IOException {
		service.updateTicket(request, response);
	}
	
	/**
	 * Modifies an existing ticket and member to reflect task assignment
	 * @param request
	 * @param response
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping("/ticket-assign")
	public void assignTicket(final HttpServletRequest request, final HttpServletResponse response)
	throws MessagingException, IOException {
	service.assignTicket(request, response);
}

	/**
	 * Returns a list of all tickets in the table
	 * @return
	 */
	@GetMapping("/tickets")
	public List<Ticket> getTickets() {
		return repository.findAll();
	}

	/**
	 * Returns a single ticket as specified by its id
	 * @param ticketNumber
	 * @return
	 */
	@GetMapping("/ticket-detail")
	@ResponseBody
	public Ticket getTicketByNumber(@RequestParam final String ticketNumber) {
		return service.getTicketByNumber(ticketNumber);
	}

	/**
	 * Deletes a ticket from the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@DeleteMapping("/ticket")
	public void deleteTicket(final HttpServletRequest request) {
		service.removeTicket(request);
	}
}
