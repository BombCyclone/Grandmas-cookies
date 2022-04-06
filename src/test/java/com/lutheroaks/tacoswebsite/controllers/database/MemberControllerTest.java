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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class MemberControllerTest {

    @InjectMocks
    private MemberController controller;
    
    @Mock
    private MemberRepo repository;

    @BeforeEach 
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void addMemberDoesNotPreviouslyExist() throws IOException{
        // we don't actually want to save to our database here, just return null
        doReturn(null).when(repository).save(any(Member.class));
        HttpServletRequest  request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse  response = Mockito.mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect(anyString());
        // mock adding a new member 
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        // return null when querying given email, indicating the user email does not exist currently
        when(repository.findMemberByEmail(anyString())).thenReturn(null);
        controller.addMember(request, response);
        // confirm that the form would have successfully added the member and refreshed the page
        verify(response, times(1)).sendRedirect("member-table");
    }

    @Test
    public void addMemberAlreadyExists() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = Mockito.mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect(anyString());
        // mock adding a new member 
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        // return a member when querying by email, indicating the member already exists
        when(repository.findMemberByEmail(anyString())).thenReturn(new Member());
        controller.addMember(request, response);
        // confirm that we would have been routed to the error page
        verify(response, times(1)).sendRedirect("error");
    }

    @Test
    public void getMembersTest(){
        // mockList will be returned by the findAll method
        List<Member> mockList = new ArrayList<Member>();
        Member fakeMember = new Member();
        fakeMember.setMemberId(1);
        fakeMember.setFirstName("Herbert");
        fakeMember.setLastName("Malcolm");
        fakeMember.setEmail("idonthaveanemail@fakemail.com");
        mockList.add(fakeMember);
        // return the mockList instead of querying the database
        when(repository.findAll()).thenReturn(mockList);
        List<Member> retVal = controller.getMembers();
        // make sure that the returned data is our mockList
        assertEquals("Herbert", retVal.get(0).getFirstName());
        assertEquals("Malcolm", retVal.get(0).getLastName());
        assertEquals("idonthaveanemail@fakemail.com", retVal.get(0).getEmail());
        assertEquals(1, retVal.get(0).getMemberId());
    }

    @Test
    void deleteMemberSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("memberId")).thenReturn("1");
        doNothing().when(repository).deleteMemberById(anyInt());
        controller.deleteMember(request, response);
        verify(response, times(1)).sendRedirect("member");
    }

}
