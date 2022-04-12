package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.comment.Comment;
import com.lutheroaks.tacoswebsite.entities.comment.CommentRepo;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class CommentControllerTest {
    
    @InjectMocks
    private CommentController controller;

    @Mock
    private CommentRepo repository;

    @Mock
    private MemberRepo memberRepo;

	@Mock
	private TicketRepo ticketRepo;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCommentTest() {
        // we don't actually want to save to our database here
        doReturn(null).when(repository).save(any(Comment.class));
        
        HttpServletRequest request = mock(HttpServletRequest.class);
        Principal mockPrince = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(mockPrince);
        when(mockPrince.getName()).thenReturn("Charles");
        when(memberRepo.findMemberByEmail(anyString())).thenReturn(new Member());
        when(request.getParameter("content")).thenReturn("test comment");
        when(request.getParameter("ticketId")).thenReturn("1");
        when(ticketRepo.findTicketById(anyInt())).thenReturn(new Ticket());

        String retVal = controller.addComment(request);
        assertEquals("A new comment was added!", retVal);
    }

    @Test
    public void getCommentsTest() {
        List<Comment> mockList = new ArrayList<>();
        Comment toAdd = new Comment();
        toAdd.setContent("this is a test");
        mockList.add(toAdd);
        when(repository.findAll()).thenReturn(mockList);
        List<Comment> retVal = controller.getComments();
        assertEquals("this is a test", retVal.get(0).getContent());
    }

    @Test
    void deleteCommentSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("commentId")).thenReturn("1");
        doNothing().when(repository).deleteCommentById(anyInt());
        controller.deleteComment(request, response);
        verify(response, times(1)).sendRedirect("active-tickets");
    }
}
