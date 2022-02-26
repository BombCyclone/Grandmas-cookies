package com.lutheroaks.tacoswebsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tacosemailservice@gmail.com");
        simpleMailMessage.setTo("jdkata1@ilstu.edu");
        simpleMailMessage.setSubject("About getting this  working");
        simpleMailMessage.setText("Hell world");
        javaMailSender.send(simpleMailMessage);
        System.out.println("Sent message successfully");
    }
}
