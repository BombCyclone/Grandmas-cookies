package com.lutheroaks.tacoswebsite.entities.member;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class MemberServiceTest {

    @InjectMocks
    private MemberService service;

    @Mock
    private MemberRepo repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMemberDoesNotPreviouslyExist() throws IOException{
        // we don't actually want to save to our database here, just return null
        doReturn(null).when(repository).save(any(Member.class));
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect(anyString());
        // mock adding a new member 
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        // return null when querying given email, indicating the user email does not exist currently
        when(repository.findMemberByEmail(anyString())).thenReturn(null);
        // call the method to be tested
        service.createMember(request, response);
        // confirm that the form would have successfully added the member and refreshed the page
        verify(response, times(1)).sendRedirect("member-table");
    }

    @Test
    void addMemberAlreadyExists() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect(anyString());
        // mock adding a new member 
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        // return a member when querying by email, indicating the member already exists
        when(repository.findMemberByEmail(anyString())).thenReturn(new Member());
        service.createMember(request, response);
        // confirm that we would have been routed to the error page
        verify(response, times(1)).sendRedirect("error");
    }

    @Test
    void updateMemberSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect(anyString());
        // mock adding a new member 
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(request.getParameter("memberId")).thenReturn("10");
        when(request.getParameter("password")).thenReturn("password");
        // we should get a member back when we try to find by ID
        when(repository.findMemberById(anyInt())).thenReturn(new Member());
        // don't actually save the member for the test
        doReturn(null).when(repository).save(any(Member.class));
        // don't actually encode the password for this test
        doReturn("totally secure password").when(passwordEncoder).encode(anyString());
        // call the method to be tested
        service.updateMember(request, response);
        // confirm the response indicates success
        verify(response, times(1)).sendRedirect("member-table");
    }

    @Test
    void deleteMemberTest(){
        // mock the request and its parameters
        HttpServletRequest  request = mock(HttpServletRequest.class);
        when(request.getParameter("memberId")).thenReturn("1");
        // don't actually delete a member in our test
        doNothing().when(repository).deleteMemberById(anyInt());
        // call the method to be tested
        service.removeMember(request);
        // confirm the expected method was called
        verify(repository, times(1)).deleteMemberById(anyInt());
    }
    
}
