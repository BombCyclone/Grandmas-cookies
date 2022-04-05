package com.lutheroaks.tacoswebsite.controllers.database;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.controllers.database.helpers.TicketHelpers;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;
import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.ticket.Ticket;
import com.lutheroaks.tacoswebsite.ticket.TicketRepo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
	private TicketRepo ticketRepo;

	@Mock
	private ResidentRepo residentRepo;
    
    @Mock
    private TicketHelpers helper;
    
    @Mock
    private JavaMailSender mailSender;
    
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void SubmitTicketSuccessful() throws IOException, MessagingException{ // test if email sent
        Resident tempResident = new Resident();
        HttpServletRequest  request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse  response = Mockito.mock(HttpServletResponse.class);
        

        //request.setAttribute("message", "I Need Help");

        
        when(request.getParameter("message")).thenReturn("Please help spilled jello on keyboard");
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(request.getParameter("roomNumber")).thenReturn("131");

        when(helper.findResident(anyString(),anyString(),anyInt(),any(ResidentRepo.class))).thenReturn(tempResident);
        doReturn(null).when(residentRepo).save(any(Resident.class));
        doReturn(null).when(ticketRepo).save(any(Ticket.class));
        when(helper.sendEmail(anyString(),anyString(),anyString(),any(JavaMailSender.class))).thenReturn(true);
        ticketController.submitTicket(request, response);
        verify(response, times(1)).sendRedirect("index");
    }


    // need test if email did not send, but success
    
    @Test
    public void SubmitTicketFailure() throws IOException, MessagingException{
        Resident tempResident = new Resident();
        HttpServletRequest  request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse  response = Mockito.mock(HttpServletResponse.class);
        when(request.getParameter("message")).thenReturn("Please help spilled jello on keyboard");
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(request.getParameter("roomNumber")).thenReturn("131");
        
        when(helper.findResident(anyString(),anyString(),anyInt(),any(ResidentRepo.class))).thenReturn(tempResident);
        doReturn(null).when(residentRepo).save(any(Resident.class));
        doReturn(null).when(ticketRepo).save(any(Ticket.class));
        when(helper.sendEmail(anyString(),anyString(),anyString(),any(JavaMailSender.class))).thenReturn(false);
        ticketController.submitTicket(request, response);
        verify(response, times(1)).sendRedirect("error");
    } 
    // intentional failure case

    @Test
    public void getTicketsTest(){
        List<Ticket> mockTicketsList = new ArrayList<>();
        Ticket toAdd = new Ticket();
        toAdd.setIssueDesc("TV cracked help");
        mockTicketsList.add(toAdd);
        when(ticketRepo.findAll()).thenReturn(mockTicketsList);
        List<Ticket> retVal = ticketController.getTickets();
        assertEquals("TV cracked help", retVal.get(0).getIssueDesc());
    }
}
