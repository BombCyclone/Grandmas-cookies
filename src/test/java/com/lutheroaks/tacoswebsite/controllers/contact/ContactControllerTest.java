package com.lutheroaks.tacoswebsite.controllers.contact;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.utils.ContactUs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

@SpringBootTest
public final class ContactControllerTest {

    @InjectMocks
    private ContactController controller;
    @Mock
    private ContactUs contactUs;

    // set up a null session so that the test email messages aren't sent
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contactUsCalled() throws MessagingException, IOException{
        // mock the parameters for the send email method
        HttpServletRequest  mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        // don't actually send an email
        doNothing().when(contactUs).sendContactEmail(any(), any());
        // call the method in the controller
        controller.contactUs(mockRequest, mockResponse);
        // assert that the expected method was called
        verify(contactUs, times(1)).sendContactEmail(any(), any());
    }

}
