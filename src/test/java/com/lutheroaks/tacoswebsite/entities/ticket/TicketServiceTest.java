package com.lutheroaks.tacoswebsite.entities.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.utils.EmailSender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public final class TicketServiceTest {

    @InjectMocks
    private TicketService service;

	@Mock
	private TicketRepo ticketRepo;

	@Mock
	private ResidentRepo residentRepo;

	@Mock
	private EmailSender sender;

	@Mock
	private JavaMailSender mailSender;

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
        when(residentRepo.findResidentByName(anyString(), anyString())).thenReturn(mockList);
        Resident retVal = service.findResident("fname", "lname", 10);
        assertEquals("Phil", retVal.getFirstName());
    }

    @Test
    void findResidentNoMatchFound(){
        when(residentRepo.findResidentByName(anyString(), anyString())).thenReturn(new ArrayList<>());
        Resident retVal = service.findResident("John", "Smith", 100);
        assertEquals("John", retVal.getFirstName());
        assertEquals("Smith", retVal.getLastName());
        assertEquals(100, retVal.getRoomNum(), 0);
    }
    
    // @Test
    // void createTicketSuccessful(){
    //     // mock the request and its parameters
    //     HttpServletRequest  request = mock(HttpServletRequest.class);
    //     HttpServletResponse  response = mock(HttpServletResponse.class);
    //     when(request.getParameter("message")).thenReturn("I need help");
    //     when(request.getParameter("fname")).thenReturn("Dorothy");
    //     when(request.getParameter("lname")).thenReturn("Jenkins");
    //     when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
    //     when(request.getParameter("roomNumber")).thenReturn("131");

    //     Resident tempResident = new Resident();
    //     when(helper.findResident(anyString(),anyString(),anyInt())).thenReturn(tempResident);
    //     doReturn(null).when(residentRepo).save(any(Resident.class));
    //     doReturn(null).when(ticketRepo).save(any(Ticket.class));
    //     when(sender.sendEmail(anyString(),anyString(),anyString(),any(JavaMailSender.class))).thenReturn(true);
    //     ticketController.submitTicket(request, response);
    // }

}
