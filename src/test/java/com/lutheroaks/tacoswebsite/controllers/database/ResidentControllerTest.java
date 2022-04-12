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

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class ResidentControllerTest {
    
    @InjectMocks
    private ResidentController controller;
    
    @Mock
    private ResidentRepo repository;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addResidentDoesNotPreviouslyExist(){
        // we don't actually want to save to our database here
        doReturn(null).when(repository).save(any(Resident.class));
        // return an empty list
        List<Resident> mockReturn = new ArrayList<>();
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(mockReturn);
        // call the method to test
        String retVal = controller.addResident("Rupert", "Thompson", 101);
        // make sure the return matches the expected string
        assertEquals("A new resident was added!", retVal);
    }

    @Test
    public void addResidentAlreadyExists() {
        // return an list that is not empty
        List<Resident> mockReturn = new ArrayList<>();
        Resident toAdd = new Resident();
        mockReturn.add(toAdd);
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(mockReturn);
        // call the method to test
        String retVal = controller.addResident("Rupert", "Thompson", 101);
        // make sure the return matches the expected string
        assertEquals("Resident already in system", retVal);
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
    void deleteResidentSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("residentId")).thenReturn("1");
        doNothing().when(repository).deleteResidentByResidentId(anyInt());
        controller.deleteResident(request, response);
        verify(response, times(1)).sendRedirect("resident");
    }
}
