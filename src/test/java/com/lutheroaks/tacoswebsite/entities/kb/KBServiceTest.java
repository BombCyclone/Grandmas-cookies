package com.lutheroaks.tacoswebsite.entities.kb;

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
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class KBServiceTest {

    @InjectMocks
    private KBService service;

    @Mock
    private KBPostRepo repository;

    @Mock
    private AuthenticatedDetails authenticatedDetails;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createKBPostTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("title")).thenReturn("How to log in");
        when(request.getParameter("content")).thenReturn("Type in your username and password");
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // don't save this fake post
        doReturn(null).when(repository).save(any(KBPost.class));
        // call the method to be tested
        service.createPost(request);
        // confirm that this was a successful case and save was called
        verify(repository, times(1)).save(any(KBPost.class));
    }

    @Test
    void updatedKBPostTest()throws MessagingException, IOException{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        KBPost fakekbpost = new KBPost();

        
        doNothing().when(response).sendRedirect("index");

        when(repository.findPostById(anyInt())).thenReturn(fakekbpost);
        
        when(request.getParameter("kbpostID")).thenReturn("1");
        when(request.getParameter("content")).thenReturn("changing up words");
        when(request.getParameter("title")).thenReturn("faketitle");
      
        doReturn(null).when(repository).save(any(KBPost.class));
      
        service.updateKBPost(request, response);
        verify(response, times(1)).sendRedirect("index");
    }

    @Test
    void removeKBPostTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("postId")).thenReturn("1");
        // don't delete this fake post
        doNothing().when(repository).deleteKBPostById(anyInt());
        // call the method to be tested
        service.removePost(request);
        // confirm that this was a successful case and save was called
        verify(repository, times(1)).deleteKBPostById(anyInt());
    }
    
}
