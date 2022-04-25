package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public final class ResidentControllerTest {
    
    @InjectMocks
    private ResidentController controller;

    @Mock
    private ResidentService service;
    
    @Mock
    private ResidentRepo repository;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addResidentTest() {
        // don't actually call the method to create a member
        doNothing().when(service).createResident(anyString(), anyString(), anyInt());
        // call the method to be tested
        controller.addResident("firstName", "lastName", 1);
        // confirm that the expected method was called
        verify(service, times(1)).createResident(anyString(), anyString(), anyInt());
    }

    @Test
    public void getResidentsTest(){
        // mockList will be returned by the findAll method
        List<Resident> mockList = new ArrayList<Resident>();
        Resident fakeResident = new Resident();
        fakeResident.setFirstName("Gloria");
        fakeResident.setLastName("Simpson");
        fakeResident.setRoomNum(327);
        mockList.add(fakeResident);
        // return the mockList instead of querying the database
        when(repository.findAll()).thenReturn(mockList);
        List<Resident> retVal = controller.getResidents();
        // make sure that the returned data is the mockList
        assertEquals("Gloria", retVal.get(0).getFirstName());
        assertEquals("Simpson", retVal.get(0).getLastName());
        assertEquals(327, retVal.get(0).getRoomNum());
    }

    @Test
    void deleteResidentSuccess() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete a member
        doNothing().when(service).removeResident(request);
        // call the method to be tested
        controller.deleteResident(request);
        // confirm that the expected method was called
        verify(service, times(1)).removeResident(request);
    }

    @Test
    void updateResidentSuccess() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete a member
        doNothing().when(service).updateResident(request);
        // call the method to be tested
        controller.updateResident(request);
        // confirm that the expected method was called
        verify(service, times(1)).updateResident(request);
    }
}
