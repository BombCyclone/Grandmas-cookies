package com.lutheroaks.tacoswebsite.controller;

import static org.mockito.ArgumentMatchers.any;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.junit.jupiter.api.Test;
import com.lutheroaks.tacoswebsite.controller.ContactController;

@SpringBootTest
public class ContactControllerTest {

    @Autowired
    private ContactController controller;
    private EmailServiceImpl emailServiceImpl;
    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;
    @BeforeEach
    public void before() {
        SimpleMailMessage newSimpleMailMessage = new SimpleMailMessage();
        javaMailSender = Mockito.mock(JavaMailSender.class);
        when(javaMailSender.send(any())).thenReturn(mimeMessage);
        emailServiceImpl = new EmailService(javaMailSender);
    }
    @Test
    public void emailSent(){
        String testName = "Tester";
        String testMessage = "Test message";
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        newReq.setAttribute("name", testName);
        newReq.setAttribute("message", testMessage);
        JavaMailSender mockSender = Mockito.mock(JavaMailSender.class);
        
        when(mockSender.send(any())).thenReturn(null);
        String retVal = controller.sendMail(newReq);

        //assertEquals("index", retVal)
    }

    
}
