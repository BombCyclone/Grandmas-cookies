package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResidentControllerTest {
    
    @Autowired
    private ResidentController controller;
    @Mock
    private ResidentRepo repository;

    @BeforeEach
    public void mockSetup() {
        controller = new ResidentController(repository);
    }

    @Test
    public void addResidentDoesNotPreviouslyExist(){
        // we don't actually want to save to our database here
        doReturn(null).when(repository).save(any(Resident.class));
        // return an empty list
        List<Object> mockReturn = new ArrayList<>();
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(mockReturn);
        // call the method to test
        String retVal = controller.addResident("Rupert", "Thompson", 101);
        // make sure the return matches the expected string
        assertEquals("A new resident was added!", retVal);
    }

    @Test
    public void addResidentAlreadyExists() {
        // return an list that is not empty
        List<Object> mockReturn = new ArrayList<>();
        Object toAdd = new Object();
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
        Resident fakeResident = new Resident("Gloria", "Simpson", 327);
        mockList.add(fakeResident);
        // return the mockList instead of querying the database
        when(repository.findAll()).thenReturn(mockList);
        List<Resident> retVal = controller.getResidents();
        // make sure that the returned data is the mockList
        assertEquals("Gloria", retVal.get(0).getFirstName());
        assertEquals("Simpson", retVal.get(0).getLastName());
        assertEquals(327, retVal.get(0).getRoomNum());
    }
}
