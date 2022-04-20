package com.lutheroaks.tacoswebsite.entities.comment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class CommentServiceTest {
    
    @InjectMocks
    private CommentService service;

    @Mock
    private CommentRepo repository;

    @Mock
    private TicketRepo ticketRepo;

    @Mock
    private AuthenticatedDetails authenticatedDetails;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommentSuccess(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("content")).thenReturn("test comment");
        when(request.getParameter("ticketId")).thenReturn("1");
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // mock the call to get the associated ticket
        when(ticketRepo.findTicketById(anyInt())).thenReturn(new Ticket());
        // don't save this fake comment
        doReturn(null).when(repository).save(any(Comment.class));
        // call the method to be tested
        service.createComment(request);
        // confirm that this was a successful case and save was called
        verify(repository, times(1)).save(any(Comment.class));
    }

    @Test
    void  updateCommentTestSuccess()throws MessagingException, IOException{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Comment fakeComment = new Comment();
        
        /*
        Simulate function calls to return nothing. 



        */
        verify(response, times(1)).sendRedirect("index");
    }

    @Test
    void removeCommentSuccess(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("commentId")).thenReturn("1");
        // don't actually try to delete this fake comment
        doNothing().when(repository).deleteCommentById(anyInt());
        // call the method to be tested
        service.removeComment(request);
        // confirm that this was a successful case and deleteCommentById was called
        verify(repository, times(1)).deleteCommentById(anyInt());
    }
}
