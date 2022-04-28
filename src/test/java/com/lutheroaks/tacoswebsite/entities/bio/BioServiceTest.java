package com.lutheroaks.tacoswebsite.entities.bio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class BioServiceTest {
    
    @InjectMocks
    private BioService service;

    @Mock
    private BioRepo repository;

    @Mock
    private AuthenticatedDetails authenticatedDetails;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBioSuccess() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("major")).thenReturn("education");
        when(request.getParameter("hometown")).thenReturn("Normal");
        when(request.getParameter("background")).thenReturn("something interesting");
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // don't save this fake bio
        doReturn(null).when(repository).save(any(Bio.class));
        // call the method to be tested
        service.createBio(request);
        // confirm that this was a successful case and save was called
        verify(repository, times(1)).save(any(Bio.class));
    }

    @Test
    void retrieveBioTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // create a Bio object to return
        Bio bio = new Bio();
        bio.setMajor("Math");
        when(repository.findBioByMember(any(Member.class))).thenReturn(bio);
        Bio retVal = service.retrieveBio(request);
        // confirm that the returned Bio object is the same as what was created
        assertEquals("Math", retVal.getMajor()); 
    }

    @Test
    void removeBioTest() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // create a Bio object to return
        Bio bio = new Bio();
        bio.setBioId(1);
        when(repository.findBioByMember(any(Member.class))).thenReturn(bio);
        doNothing().when(repository).deleteBioById(anyInt());
        // call the method to be tested
        service.removeBio(request, response);
        // confirm that this was a successful case and delete was called
        verify(repository, times(1)).deleteBioById(anyInt());
        verify(response, times(1)).sendRedirect("index");
    }

    @Test
    void updateBioTest(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Member test = new Member();
        Bio temp = new Bio();        
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(test);
        when(repository.findBioByMember(test)).thenReturn(temp);
        when(request.getParameter("backgroundInfo")).thenReturn("backgroundInfo");
        when(request.getParameter("hometown")).thenReturn("hometown");
        when(request.getParameter("major")).thenReturn("major");
        doReturn(null).when(repository).save(any());
        service.updateBio(request, response);
        verify(repository,times(1)).save(any());
    }
}
