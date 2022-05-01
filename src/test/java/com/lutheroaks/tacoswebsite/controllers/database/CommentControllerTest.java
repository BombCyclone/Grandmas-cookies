package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.comment.Comment;
import com.lutheroaks.tacoswebsite.entities.comment.CommentRepo;
import com.lutheroaks.tacoswebsite.entities.comment.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class CommentControllerTest {
    
    @InjectMocks
    private CommentController controller;

    @Mock
    private CommentService service;

    @Mock
    private CommentRepo repository;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCommentTest() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually create a comment
        doNothing().when(service).createComment(any(), any(), any());
        // call the method to be tested
        controller.addComment(request, "content", "1");
        // confirm that the expected method was called
        verify(service, times(1)).createComment(request, "content", "1");
    }

    @Test
    public void getCommentsTest() {
        // create a fake list to be returned and verify it is returned
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
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete a comment
        doNothing().when(service).removeComment(any());
        // call the method to be tested
        controller.deleteComment(request);
        // confirm that the expected method was called
        verify(service, times(1)).removeComment(request);
    }

    
    @Test
    void deleteAllCommentsSuccess() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete a comment
        doNothing().when(service).removeAllComments(any());
        // call the method to be tested
        controller.deleteAllComments(request);
        // confirm that the expected method was called
        verify(service, times(1)).removeAllComments(request);
    }

    @Test
    void updateCommentCalled() throws MessagingException, IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't actually update a comment
        doNothing().when(service).updateComment(any(), any());
        // call the controller method
        controller.updateComment(request, response);
        // confirm that the service method would have been called
        verify(service, times(1)).updateComment(any(), any());
    }
}
