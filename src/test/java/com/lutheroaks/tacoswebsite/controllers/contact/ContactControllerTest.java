package com.lutheroaks.tacoswebsite.controllers.contact;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.helper_utils.EmailSender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContactControllerTest {
    
    @InjectMocks
    private ContactController controller;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private EmailSender sender;

    // set up a null session so that the test email messages aren't sent
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendEmailSuccessAddressSpecifiedTest() throws MessagingException{
        // mock the parameters for the send email method
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock the returns of the name and message fields in the email to be sent
        when(newReq.getParameter("message")).thenReturn("test message");
        when(newReq.getParameter("fname")).thenReturn("Dorothy");
        when(newReq.getParameter("lname")).thenReturn("Jenkins");
        when(newReq.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(true);
        // call the method in the controller
        String retVal = controller.sendContactEmail(newReq);
        // assert the expected result
        assertEquals("index", retVal);
    }

    @Test
    public void sendEmailSuccessNoAddressTest() throws MessagingException{
        // mock the parameters for the send email method
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock the returns of the name and message fields in the email to be sent
        when(newReq.getParameter("message")).thenReturn("test message");
        when(newReq.getParameter("fname")).thenReturn("Dorothy");
        when(newReq.getParameter("lname")).thenReturn("Jenkins");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(true);
        // call the method in the controller
        String retVal = controller.sendContactEmail(newReq);
        // assert the expected result
        assertEquals("index", retVal);
    }

    @Test
    public void sendEmailFailureAddressSpecifiedTest() throws MessagingException{
        // mock the parameters for the send email method
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock the returns of the name and message fields in the email to be sent
        when(newReq.getParameter("message")).thenReturn("test message");
        when(newReq.getParameter("fname")).thenReturn("Dorothy");
        when(newReq.getParameter("lname")).thenReturn("Jenkins");
        when(newReq.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(false);
        // call the method in the controller
        String retVal = controller.sendContactEmail(newReq);
        // assert the expected result
        assertEquals("error", retVal);
    }

    @Test
    public void sendEmailFailureNoAddressTest() throws MessagingException{
        // mock the parameters for the send email method
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock the returns of the name and message fields in the email to be sent
        when(newReq.getParameter("message")).thenReturn("test message");
        when(newReq.getParameter("fname")).thenReturn("Dorothy");
        when(newReq.getParameter("lname")).thenReturn("Jenkins");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(false);
        // call the method in the controller
        String retVal = controller.sendContactEmail(newReq);
        // assert the expected result
        assertEquals("error", retVal);
    }
}
