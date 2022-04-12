package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutheroaks.tacoswebsite.entities.comment.Comment;
import com.lutheroaks.tacoswebsite.entities.comment.CommentRepo;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;

@RestController
public class CommentController {

	@Autowired
    private CommentRepo repository;

	@Autowired
    private MemberRepo memberRepo;

	@Autowired
	private TicketRepo ticketRepo;

	/**
	 * Adds a comment to the table
	 * @param content
	 * @return
	 */
	@PostMapping("/comment")
	public String addComment(final HttpServletRequest request) {
		Comment toAdd = new Comment();
		// get the email (user name) of the member member who is posting from the request details
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		Member poster = memberRepo.findMemberByEmail(userName);
		String content = request.getParameter("content");
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));
		Ticket ticket = ticketRepo.findTicketById(ticketId);

		toAdd.setMember(poster);
		toAdd.setContent(content);
		toAdd.setTicket(ticket);
		toAdd.setTimeStamp(Timestamp.from(Instant.now()));
		repository.save(toAdd);
		return "A new comment was added!";
	}

	/**
	 * Returns a list of all comments in the table
	 * @return
	 */
	@GetMapping("/comment")
	public List<Comment> getComments() {
		return repository.findAll();
	}

	/**
	 * Deletes a comment from the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@DeleteMapping("/comment")
	public void deleteComment(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		repository.deleteCommentById(commentId);
		response.sendRedirect("active-tickets");
	}
    
} 
