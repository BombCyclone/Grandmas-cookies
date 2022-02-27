package com.lutheroaks.tacoswebsite.controller;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContactControllerTest {

    @Autowired
    private ContactController controller;
    @Mock
    JavaMailSender mailSender;
    @Mock
    private MimeMessage mimeMessage;

    @Test
    public void sendEmailSuccessTest() throws MessagingException{
        // mock the parameters for the send email method
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock the returns of the name and message fields in the email to be sent
        Mockito.when(newReq.getParameter("message")).thenReturn("test message");
        Mockito.when(newReq.getParameter("name")).thenReturn("Dorothy Jenkins");
        // don't actually send an email when the send is called
        Mockito.doNothing().when(mailSender).send(mimeMessage);
        // call the method in the controller
        String retVal = controller.sendEmail(newReq);
        // assert the expected result
        assertEquals("index", retVal);
    }
}
