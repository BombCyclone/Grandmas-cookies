// package com.lutheroaks.tacoswebsite.controllers.database;


// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// import javax.mail.MessagingException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import com.lutheroaks.tacoswebsite.entities.resident.Resident;
// import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
// import com.lutheroaks.tacoswebsite.entities.ticket.Ticket;
// import com.lutheroaks.tacoswebsite.entities.ticket.TicketRepo;
// import com.lutheroaks.tacoswebsite.entities.ticket.TicketService;
// import com.lutheroaks.tacoswebsite.utils.EmailSender;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.mail.javamail.JavaMailSender;

// @SpringBootTest
// public final class TicketControllerTest {

//     @InjectMocks
//     private TicketController ticketController;

//     @Mock
// 	private TicketRepo ticketRepo;

// 	@Mock
// 	private ResidentRepo residentRepo;
    
//     @Mock
//     private TicketService helper;

//     @Mock
//     private EmailSender sender;
    
//     @Mock
//     private JavaMailSender mailSender;
    
//     @BeforeEach
//     public void setup(){
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void submitTicketSuccessful() throws IOException, MessagingException{
//         Resident tempResident = new Resident();
//         HttpServletRequest  request = mock(HttpServletRequest.class);
//         HttpServletResponse  response = mock(HttpServletResponse.class);
        
//         when(request.getParameter("message")).thenReturn("Please help spilled jello on keyboard");
//         when(request.getParameter("fname")).thenReturn("Dorothy");
//         when(request.getParameter("lname")).thenReturn("Jenkins");
//         when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
//         when(request.getParameter("roomNumber")).thenReturn("131");

//         when(helper.findResident(anyString(),anyString(),anyInt())).thenReturn(tempResident);
//         doReturn(null).when(residentRepo).save(any(Resident.class));
//         doReturn(null).when(ticketRepo).save(any(Ticket.class));
//         when(sender.sendEmail(anyString(),anyString(),anyString(),any(JavaMailSender.class))).thenReturn(true);
//         ticketController.submitTicket(request, response);
//         verify(response, times(1)).sendRedirect("index");
//     }
    
//     @Test
//     public void submitTicketFailure() throws IOException, MessagingException{
//         Resident tempResident = new Resident();
//         HttpServletRequest  request = mock(HttpServletRequest.class);
//         HttpServletResponse  response = mock(HttpServletResponse.class);
//         when(request.getParameter("message")).thenReturn("Please help spilled jello on keyboard");
//         when(request.getParameter("fname")).thenReturn("Dorothy");
//         when(request.getParameter("lname")).thenReturn("Jenkins");
//         when(request.getParameter("email")).thenReturn("fakeemail@gmail.com");
//         when(request.getParameter("roomNumber")).thenReturn("131");
        
//         when(helper.findResident(anyString(),anyString(),anyInt())).thenReturn(tempResident);
//         doReturn(null).when(residentRepo).save(any(Resident.class));
//         doReturn(null).when(ticketRepo).save(any(Ticket.class));
//         when(sender.sendEmail(anyString(),anyString(),anyString(),any(JavaMailSender.class))).thenReturn(false);
//         ticketController.submitTicket(request, response);
//         verify(response, times(1)).sendRedirect("error");
//     } 

//     @Test
//     public void getTicketsTest(){
//         List<Ticket> mockTicketsList = new ArrayList<>();
//         Ticket toAdd = new Ticket();
//         toAdd.setIssueDesc("TV cracked help");
//         mockTicketsList.add(toAdd);
//         when(ticketRepo.findAll()).thenReturn(mockTicketsList);
//         List<Ticket> retVal = ticketController.getTickets();
//         assertEquals("TV cracked help", retVal.get(0).getIssueDesc());
//     }

//     @Test
//     void deleteTicketSuccess() throws IOException{
//         HttpServletRequest  request = mock(HttpServletRequest.class);
//         HttpServletResponse  response = mock(HttpServletResponse.class);
//         when(request.getParameter("ticketNum")).thenReturn("1");
//         doNothing().when(ticketRepo).deleteTicketByTicketNum(anyInt());
//         ticketController.deleteTicket(request, response);
//         verify(response, times(1)).sendRedirect("active-tickets");
//     }

// }
