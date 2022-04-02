package com.lutheroaks.tacoswebsite.helper_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class TicketHelperTest {

    @InjectMocks
    private TicketHelper helper;

    @Mock
    private ResidentRepo repository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findResidentMatchFound(){
        List<Resident> mockList = new ArrayList<>();
        Resident toAdd = new Resident();
        toAdd.setFirstName("Phil");
        mockList.add(toAdd);
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(mockList);
        Resident retVal = helper.findResident("fname", "lname", 10);
        assertEquals("Phil", retVal.getFirstName());
    }

    @Test
    void findResidentNoMatchFound(){
        when(repository.findResidentByName(anyString(), anyString())).thenReturn(new ArrayList<>());
        Resident retVal = helper.findResident("John", "Smith", 100);
        assertEquals("John", retVal.getFirstName());
        assertEquals("Smith", retVal.getLastName());
        assertEquals(100, retVal.getRoomNum(), 0);
    }
    
}
