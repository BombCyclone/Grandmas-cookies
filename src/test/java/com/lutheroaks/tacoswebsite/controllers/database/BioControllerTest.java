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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.bio.Bio;
import com.lutheroaks.tacoswebsite.entities.bio.BioRepo;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class BioControllerTest {
    
    @InjectMocks
    private BioController controller;

    @Mock
    private BioRepo repository;

    @Mock
    private MemberRepo memberRepo;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBioTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("major")).thenReturn("education");
        when(request.getParameter("hometown")).thenReturn("Normal");
        when(request.getParameter("background")).thenReturn("something interesting");
        Principal mockPrince = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(mockPrince);
        when(mockPrince.getName()).thenReturn("Charles");
        when(memberRepo.findMemberByEmail(anyString())).thenReturn(new Member());
        doReturn(null).when(repository).save(any(Bio.class));
        controller.addBio(request, response);
        // confirm that this was a successful case and save was called
        verify(repository, times(1)).save(any(Bio.class));
    }

    @Test
    void getBioTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        Principal mockPrince = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(mockPrince);
        when(mockPrince.getName()).thenReturn("me@icloud.com");
        when(memberRepo.findMemberByEmail(anyString())).thenReturn(new Member());
        // create a Bio object to return
        Bio bio = new Bio();
        bio.setMajor("Math");
        when(repository.findBioByMember(any(Member.class))).thenReturn(bio);
        Bio retVal = controller.getBio(request);
        // confirm that the returned Bio object is the same as what was created
        assertEquals("Math", retVal.getMajor());
    }

    @Test
    void deleteBioSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("bioId")).thenReturn("1");
        doNothing().when(repository).deleteBioById(anyInt());
        controller.deleteBio(request, response);
        verify(response, times(1)).sendRedirect("index");
    }

}
