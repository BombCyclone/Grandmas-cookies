package com.lutheroaks.tacoswebsite.utils;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

@SpringBootTest
public final class ContactUsTest {

    @InjectMocks
    private ContactUs contactUs;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private EmailSender sender;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmailSuccessAddressSpecifiedTest() throws MessagingException, IOException{
        // mock the parameters for the send email method
        HttpServletRequest  mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        // mock the message fields to be added
        when(mockRequest.getParameter("message")).thenReturn("test message");
        when(mockRequest.getParameter("fname")).thenReturn("Dorothy");
        when(mockRequest.getParameter("lname")).thenReturn("Jenkins");
        when(mockRequest.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(true);
        // call the method to be tested
        contactUs.sendContactEmail(mockRequest, mockResponse);
        // assert the expected result
        verify(mockResponse, times(1)).sendRedirect("index");
    }

    @Test
    public void sendEmailSuccessNoAddressTest() throws MessagingException, IOException{
        // mock the parameters for the send email method
        HttpServletRequest  mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        // mock the returns of the name and message fields
        when(mockRequest.getParameter("message")).thenReturn("test message");
        when(mockRequest.getParameter("fname")).thenReturn("Dorothy");
        when(mockRequest.getParameter("lname")).thenReturn("Jenkins");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(true);
        // call the method to be tested
        contactUs.sendContactEmail(mockRequest, mockResponse);
        // assert the expected result
        verify(mockResponse, times(1)).sendRedirect("index");
    }

    @Test
    void sendEmailFailureAddressSpecifiedTest() throws MessagingException, IOException{
        // mock the parameters for the send email method
        HttpServletRequest  mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        // mock the returns of the name and message fields
        when(mockRequest.getParameter("message")).thenReturn("test message");
        when(mockRequest.getParameter("fname")).thenReturn("Dorothy");
        when(mockRequest.getParameter("lname")).thenReturn("Jenkins");
        when(mockRequest.getParameter("email")).thenReturn("fakeemail@gmail.com");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(false);
        // call the method to be tested
        contactUs.sendContactEmail(mockRequest, mockResponse);
        // assert the expected result
        verify(mockResponse, times(1)).sendRedirect("error");
    }

    @Test
    public void sendEmailFailureNoAddressTest() throws MessagingException, IOException{
        // mock the parameters for the send email method
        HttpServletRequest  mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        // mock the returns of the name and message fields
        when(mockRequest.getParameter("message")).thenReturn("test message");
        when(mockRequest.getParameter("fname")).thenReturn("Dorothy");
        when(mockRequest.getParameter("lname")).thenReturn("Jenkins");
        when(sender.sendEmail(any(), any(), any(), any())).thenReturn(false);
        // call the method to be tested
        contactUs.sendContactEmail(mockRequest, mockResponse);
        // assert the expected result
        verify(mockResponse, times(1)).sendRedirect("error");
    }
}
