package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.member.MemberService;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public final class MemberControllerTest {

    @InjectMocks
    private MemberController controller;

    @Mock
    private MemberService service;
    
    @Mock
    private MemberRepo repository;

    @Mock
    private AuthenticatedDetails authenticatedDetails;

    @BeforeEach 
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addMemberTest() throws IOException {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't actually call the method to create a member
        doNothing().when(service).createMember(any(), any());
        // call the method to be tested
        controller.addMember(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).createMember(any(), any());
    }

    @Test
    public void updateMemberDetailsTest() throws IOException {
            // mock the servlet request and its parameters
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            // don't actually call the method to create a member
            doNothing().when(service).updateMemberDetails(any(), any());
            // call the method to be tested
            controller.updateMemberDetails(request, response);
            // confirm that the expected method was called
            verify(service, times(1)).updateMemberDetails(any(), any());
    }

    @Test
    public void updatePassword() throws IOException {
            // mock the servlet request and its parameters
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            // don't actually call the method to create a member
            doNothing().when(service).updatePassword(any(), any());
            // call the method to be tested
            controller.updatePassword(request, response);
            // confirm that the expected method was called
            verify(service, times(1)).updatePassword(any(), any());
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
    public void getLoggedInMemberTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // mock the call to authenticated details
        Member toReturn = new Member();
        toReturn.setFirstName("Linda");
        doReturn(toReturn).when(authenticatedDetails).getLoggedInMember(any());
        // call the controller method
        Member retVal = controller.getLoggedInMemberDetails(request);
        // assert the return
        assertEquals("Linda", retVal.getFirstName());
    }

    @Test
    void deleteMemberTest() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete a member
        doNothing().when(service).removeMember(any());
        // call the method to be tested
        controller.deleteMember(request);
        // confirm that the expected method was called
        verify(service, times(1)).removeMember(any());
    }

}
