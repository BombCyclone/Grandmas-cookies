package com.lutheroaks.tacoswebsite.controllers.contact;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContactControllerTest {
    
    @Autowired
    private ContactController controller;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;

    // set up a null session so that the test email messages aren't sent
    @BeforeEach
    public void mockSetup() {
        mimeMessage = new MimeMessage((Session)null);
        javaMailSender = Mockito.mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        controller = new ContactController(javaMailSender);
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
        // call the method in the controller
        String retVal = controller.sendEmail(newReq);
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
        // call the method in the controller
        String retVal = controller.sendEmail(newReq);
        // assert the expected result
        assertEquals("index", retVal);
    }
}
