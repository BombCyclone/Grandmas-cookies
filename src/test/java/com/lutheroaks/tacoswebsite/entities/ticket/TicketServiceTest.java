package com.lutheroaks.tacoswebsite.entities.ticket;

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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.entities.tag.Tag;
import com.lutheroaks.tacoswebsite.entities.tag.TagRepo;
import com.lutheroaks.tacoswebsite.entities.tag.TagService;
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
    private MemberRepo memberRepo;

	@Mock
	private EmailSender sender;

	@Mock
	private JavaMailSender mailSender;

    @Mock
	private TagRepo tagRepo;

	@Mock
	private TagService tagService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findResidentMatchFound(){
        Resident phil = new Resident();
        phil.setFirstName("Phil");
        when(residentRepo.findResidentByName(anyString(), anyString())).thenReturn(phil);
        Resident retVal = service.findResident("fname", "lname", 10);
        assertEquals("Phil", retVal.getFirstName());
    }

    @Test
    void findResidentNoMatchFound(){
        when(residentRepo.findResidentByName(anyString(), anyString())).thenReturn(null);
        Resident retVal = service.findResident("John", "Smith", 100);
        assertEquals("John", retVal.getFirstName());
        assertEquals("Smith", retVal.getLastName());
        assertEquals(100, retVal.getRoomNum(), 0);
    }
    
    @Test
    void createTicketSuccessful() throws MessagingException, IOException{
        // mock the request and its parameters
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("message")).thenReturn("I need help");
        when(request.getParameter("fname")).thenReturn("Dorothy");
        when(request.getParameter("lname")).thenReturn("Jenkins");
        when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(request.getParameter("roomNumber")).thenReturn("131");
        // mock the calls to other methods within TicketService, we will test them separately
        TicketService spyService = spy(service);
        Resident tempResident = new Resident();
        doReturn(tempResident).when(spyService).findResident(anyString(),anyString(),anyInt());
        doNothing().when(spyService).sendSubmissionEmail(anyString(), any(StringBuilder.class),
                anyString(), any(HttpServletResponse.class));
        
        // don't actually save the mocked ticket and resident
        doReturn(null).when(residentRepo).save(any(Resident.class));
        doReturn(null).when(ticketRepo).save(any(Ticket.class));

        // call the method to be tested
        spyService.createTicket(request, response);
        // confirm no error was encountered
        verify(response, times(0)).sendRedirect("error");
    }

    @Test
    void sendSubmissionEmailSuccess() throws MessagingException, IOException{
        // don't actually send an email, just mock the return
        doReturn(true).when(sender).sendEmail(anyString(), anyString(), anyString(), any(JavaMailSender.class));
        HttpServletResponse response = mock(HttpServletResponse.class);
        // call the method to be tested
        service.sendSubmissionEmail("email", new StringBuilder(), "full name", response);
        // assert the resulting route
        verify(response, times(1)).sendRedirect("index");
    }

    @Test
    void sendSubmissionEmailFailure() throws MessagingException, IOException{
        // don't actually send an email, just mock the return
        doReturn(false).when(sender).sendEmail(anyString(), anyString(), anyString(), any(JavaMailSender.class));
        HttpServletResponse response = mock(HttpServletResponse.class);
        // call the method to be tested
        service.sendSubmissionEmail("email", new StringBuilder(), "full name", response);
        // assert the resulting route
        verify(response, times(1)).sendRedirect("error");
    }

    @Test
    void updateTicketSuccessful() throws MessagingException, IOException{
    
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    Ticket fakeTicket = new Ticket();
    when(request.getParameter("ticketId")).thenReturn("2");
    when(request.getParameter("ticketstatus")).thenReturn("0");
    when(request.getParameter("issueDesc")).thenReturn("Installed too much RAM from online");
    String[] tagStrings = {"computers","email"};
    when(request.getParameterValues("tags")).thenReturn(tagStrings);
    doReturn(null).when(tagRepo).findTag(anyString());
    doReturn(new Tag()).when(tagService).createTag(anyString(),any());
    doReturn(fakeTicket).when(ticketRepo).findTicketById(anyInt());
    doReturn(fakeTicket).when(ticketRepo).save(any(Ticket.class));
    service.updateTicket(request,response);
    verify(response, times(1)).sendRedirect("index");
    }


    @Test
    void assignTicketTestSuccessful() throws MessagingException, IOException{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("ticketId")).thenReturn("2");
        String[] fakes = {"1"};
        when(request.getParameterValues("memberId")).thenReturn(fakes);
        Ticket fakeTicket = new Ticket();
        doReturn(fakeTicket).when(ticketRepo).findTicketById(anyInt());
        Member fakeMember = new Member();
        doReturn(fakeMember).when(memberRepo).findMemberById(anyInt());
        doReturn(null).when(ticketRepo).save(any(Ticket.class));
        service.assignTicket(request,response);
        verify(response, times(1)).sendRedirect("index");
    }

    @Test
    void removeTicketTest() {
        // don't atually delete a ticket
        doNothing().when(ticketRepo).deleteTicketByTicketNum(anyInt());
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("ticketNum")).thenReturn("1");
        // call the method to be tested
        service.removeTicket(request);
        // confirm that the ticket would have been deleted
        verify(ticketRepo, times(1)).deleteTicketByTicketNum(anyInt());
    }

    @Test
    void getTicketByNumberTest() {
        Ticket fakeTicket = new Ticket();
        fakeTicket.setIssueDesc("issueDesc");
        doReturn(fakeTicket).when(ticketRepo).findTicketById(anyInt());
        Ticket returnedTicket = service.getTicketByNumber("1");
        assertEquals("issueDesc", returnedTicket.getIssueDesc());
    }
}
