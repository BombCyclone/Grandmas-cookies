package com.lutheroaks.tacoswebsite.controllers.database;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.bio.BioRepo;
import com.lutheroaks.tacoswebsite.entities.bio.BioService;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class BioControllerTest {
    
    @InjectMocks
    private BioController controller;

    @Mock
    private BioRepo repository;

    @Mock
    private BioService service;

    @Mock
    private MemberRepo memberRepo;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBioTest() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(service).createBio(any(), any());
        // call the method to be tested
        controller.addBio(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).createBio(request, response);
    }

    @Test
    void getBioTest(){
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        doReturn(null).when(service).retrieveBio(any());
        // call the method to be tested
        controller.getBio(request);
        // confirm that the expected method was called
        verify(service, times(1)).retrieveBio(request);
    }

    @Test
    void deleteBioSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        doNothing().when(service).removeBio(any(), any());
        // call the method to be tested
        controller.deleteBio(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).removeBio(request, response);
    }

}
