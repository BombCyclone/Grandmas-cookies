package com.lutheroaks.tacoswebsite.controllers.database;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
import com.lutheroaks.tacoswebsite.entities.ticket.TicketService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public final class TicketControllerTest {

    @InjectMocks
    private TicketController controller;

    @Mock
	private TicketRepo repository;

    @Mock
    private TicketService service;
    
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addTicketCalled() throws IOException, MessagingException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't actually create the fake ticket
        doNothing().when(service).createTicket(any(), any());
        // call the method to be tested
        controller.addTicket(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).createTicket(any(), any());
    }

    @Test
    public void getTicketsTest(){
        List<Ticket> mockTicketsList = new ArrayList<>();
        Ticket toAdd = new Ticket();
        toAdd.setIssueDesc("TV cracked help");
        mockTicketsList.add(toAdd);
        when(repository.findAll()).thenReturn(mockTicketsList);
        List<Ticket> retVal = controller.getTickets();
        assertEquals("TV cracked help", retVal.get(0).getIssueDesc());
    }

    @Test
    void deleteTicketSuccess() throws IOException{
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete the fake post
        doNothing().when(service).removeTicket(any());
        // call the method to be tested
        controller.deleteTicket(request);
        // confirm that the expected method was called
        verify(service, times(1)).removeTicket(any());
    }

}
