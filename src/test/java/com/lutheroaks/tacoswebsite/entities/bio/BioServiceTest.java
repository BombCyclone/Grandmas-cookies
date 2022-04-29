package com.lutheroaks.tacoswebsite.entities.bio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

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
    void retrieveBioAlreadyExists(){
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
    void retrieveBioNotCreatedYet() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // mock the call to get the currently logged in user
        when(authenticatedDetails.getLoggedInMember(any())).thenReturn(new Member());
        // don't return a bio, indicating this member does not have one yet
        when(repository.findBioByMember(any(Member.class))).thenReturn(null);
        // spy on the service class, don't call create bio here
        Bio bio = new Bio();
        bio.setMajor("Math");
        BioService spyService = spy(service);
        doReturn(bio).when(spyService).createBio(any());
        // call the method to be tested
        Bio retVal = spyService.retrieveBio(request);
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

    @Test
    void addProfilePictureTest() throws IOException, ServletException{
        // mock the parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        MultipartFile file = mock(MultipartFile.class);
        // spy on the service; don't call retrieveBio here
        BioService spyService = spy(service);
        doReturn(new Bio()).when(spyService).retrieveBio(any());
        doReturn(null).when(file).getBytes();
        // don't save in the test
        doReturn(null).when(repository).save(any());
        // call the method to be tested
        spyService.addProfilePicture(file, request);
        // confirm that the save would have occured
        verify(repository, times(1)).save(any());
    }

    @Test
    void retrieveProfilePictureTest() throws IOException{
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't try to retrieve a photo from the database in the test
        byte[] image = "photo data".getBytes();
        doReturn(image).when(repository).getProfilePicture(anyInt());
        // don't set these values on the response here
        doNothing().when(response).setContentType(anyString());

        ServletOutputStream fakeStream = mock(ServletOutputStream.class);
        doReturn(fakeStream).when(response).getOutputStream();
        doNothing().when(fakeStream).write(any());
        doNothing().when(fakeStream).write(any());
        // call the method to be tested
        service.retrieveProfilePicture("1", response);
        // confirm that the outputstream was called twice
        verify(response, times(2)).getOutputStream();
    }
}
