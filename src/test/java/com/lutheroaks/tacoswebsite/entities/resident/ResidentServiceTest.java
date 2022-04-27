package com.lutheroaks.tacoswebsite.entities.resident;

import static org.mockito.ArgumentMatchers.any;
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

public final class ResidentServiceTest {
    
    @InjectMocks
    private ResidentService service;

    @Mock
    private ResidentRepo repository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createResidentSuccessCase() throws IOException{
        // we don't actually want to save to our database here
        doReturn(null).when(repository).save(any(Resident.class));
        // return an empty list when checking to see if resident exists
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(new Resident());
        // call the method to test
        when(request.getParameter("roomNumber")).thenReturn("100");
        when(request.getParameter("fName")).thenReturn("jeff");
        when(request.getParameter("lName")).thenReturn("bezos");
        doReturn(null).when(repository).save(any());
        doReturn(null).when(repository).findResidentByName(anyString(), anyString());
        service.createResident(request,response);
        // confirm that Rupert would have been saved
        verify(repository, times(1)).save(any());
    }

    @Test
    void createResidentAlreadyExists() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(new Resident());
        // call the method to test
        when(request.getParameter("roomNumber")).thenReturn("100");
        when(request.getParameter("fName")).thenReturn("jeff");
        when(request.getParameter("lName")).thenReturn("bezos");
        doReturn(new Resident()).when(repository).findResidentByName(anyString(), anyString());
        service.createResident(request,response);
        // confirm that Rupert would not be saved this time
        verify(repository, times(0)).save(any());
    }

    @Test
    void removeResidentTest(){
        // mock the request and its parameters
        HttpServletRequest  request = mock(HttpServletRequest.class);
        when(request.getParameter("residentId")).thenReturn("1");
        // don't actually try to delete during the test
        doNothing().when(repository).deleteResidentByResidentId(1);
        // call the method to be tested
        service.removeResident(request);
        // confirm that we would have deleted this resident
        verify(repository, times(1)).deleteResidentByResidentId(1);
    }

    @Test
    void updateResidentTest(){
        //mock the request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("roomNumber")).thenReturn("100");
        when(request.getParameter("firstName")).thenReturn("jeff");
        when(request.getParameter("lastName")).thenReturn("bezos");
        Resident tester = new Resident();
        when(repository.findResidentById(1)).thenReturn(tester);
        doReturn(null).when(repository).save(any());
        service.updateResident(request);
        verify(repository, times(1)).save(any());
    }
}
