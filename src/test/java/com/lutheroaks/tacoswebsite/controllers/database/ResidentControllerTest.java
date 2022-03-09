package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    public void addResidentTest(){
        // we don't actually want to save to our database here, just return null
        Mockito.doReturn(null).when(repository).save(Mockito.any(Resident.class));
        // call the method to test
        String retVal = controller.addResident("Rupert", "Thompson", 101);
        // make sure the return type matches the expected string
        assertEquals("A new resident was added!", retVal);
    }

    @Test
    public void getResidentsTest(){
        // mockList will be returned by the findAll method
        List<Resident> mockList = new ArrayList<Resident>();
        Resident fakeResident = new Resident("Gloria", "Simpson", 327);
        mockList.add(fakeResident);
        // return the mockList instead of querying the database
        Mockito.when(repository.findAll()).thenReturn(mockList);
        List<Resident> retVal = controller.getResidents();
        // make sure that the returned data is the mockList
        assertEquals("Gloria", retVal.get(0).getFirstName());
        assertEquals("Simpson", retVal.get(0).getLastName());
        assertEquals(327, retVal.get(0).getRoomNum());
    }
}
