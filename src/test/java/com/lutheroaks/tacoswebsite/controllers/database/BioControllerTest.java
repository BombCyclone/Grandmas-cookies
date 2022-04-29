package com.lutheroaks.tacoswebsite.controllers.database;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.bio.BioRepo;
import com.lutheroaks.tacoswebsite.entities.bio.BioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

public final class BioControllerTest {
    
    @InjectMocks
    private BioController controller;

    @Mock
    private BioRepo repository;

    @Mock
    private BioService service;


    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBioTest() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);

        doReturn(null).when(service).createBio(any());
        // call the method to be tested
        controller.addBio(request);
        // confirm that the expected method was called
        verify(service, times(1)).createBio(request);
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

    @Test
    void updateBioCalled() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(service).updateBio(request, response);
        // call the method to be tested
        controller.updateBio(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).updateBio(request, response);
    }

    @Test
    void uploadPictureCalled() throws IOException, ServletException{
        // mock the parameters
        MultipartFile file = mock(MultipartFile.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually invoke the service method call
        doNothing().when(service).addProfilePicture(any(), any());
        // call the controller
        controller.uploadPicture(file, request);
        // assert that the service would have been called
        verify(service, times(1)).addProfilePicture(file, request);
    }

    @Test
    void getPictureCalled() throws IOException, ServletException{
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't actually invoke the service method call
        doNothing().when(service).retrieveProfilePicture(anyString(), any());
        // call the controller
        controller.getPicture("1", response);
        // assert that the service would have been called
        verify(service, times(1)).retrieveProfilePicture(anyString(), any());
    }

}
