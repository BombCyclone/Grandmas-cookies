package com.lutheroaks.tacoswebsite.entities.comment;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    public void createComment(final HttpServletRequest request, @RequestParam final String ticketContent, 
                @RequestParam final  String ticketNum) {
        // get the member who is making the comment based on their login credentials
        Member poster = authenticatedDetails.getLoggedInMember(request);
        // find the associated ticket
		int ticketId = Integer.parseInt(ticketNum);
		Ticket ticket = ticketRepo.findTicketById(ticketId);
        // create the comment and set its fields
        Comment toAdd = new Comment();
		toAdd.setMember(poster);
		toAdd.setContent(ticketContent);
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

    /**
     * Deletes all comments by ticket
     * @param request
     */
    public void removeAllComments(final HttpServletRequest request) {
		int ticketNum = Integer.parseInt(request.getParameter("ticketNum"));
		repository.deleteCommentsByTicket(ticketNum);
    }

    /**
     * 
     * @param request
     * @param response
     * @throws MessagingException
     * @throws IOException
     */
    public void updateComment(final HttpServletRequest request,
                final HttpServletResponse response) throws MessagingException, IOException { 
 
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        Comment updatedComment = repository.findCommentById(commentID);
        
        String content = request.getParameter("content");
        updatedComment.setContent(content);
    
        repository.save(updatedComment);
        response.sendRedirect("index");
    }
}
