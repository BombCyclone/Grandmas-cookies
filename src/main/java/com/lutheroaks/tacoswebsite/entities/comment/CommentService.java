package com.lutheroaks.tacoswebsite.entities.comment;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepo repository;

	@Autowired
	private TicketRepo ticketRepo;

    @Autowired
    private AuthenticatedDetails authenticatedDetails;

    /**
     * Creates and saves a new comment
     * @param request
     */
    public void createComment(final HttpServletRequest request) {
        // get the member who is making the comment based on their login credentials
        Member poster = authenticatedDetails.getLoggedInMember(request);
        String content = request.getParameter("content");
        // find the associated ticket
		int ticketId = Integer.parseInt(request.getParameter("ticketId"));
		Ticket ticket = ticketRepo.findTicketById(ticketId);
        // create the comment and set its fields
        Comment toAdd = new Comment();
		toAdd.setMember(poster);
		toAdd.setContent(content);
		toAdd.setTicket(ticket);
		toAdd.setTimeStamp(Timestamp.from(Instant.now()));
		repository.save(toAdd);
    }

    /**
     * Delets a comment from the database
     * @param request
     */
    public void removeComment(final HttpServletRequest request) {
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		repository.deleteCommentById(commentId);
    }
}
