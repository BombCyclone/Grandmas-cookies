package com.lutheroaks.tacoswebsite.helper_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.mail.Session;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lutheroaks.tacoswebsite.controllers.database.MemberController;
import com.lutheroaks.tacoswebsite.member.Member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public final class EmailSenderTest {

    @InjectMocks 
    private EmailSender sender;

    @Mock
    private MemberController controller;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void sendEmailTest() throws MessagingException{
        // we will test getMemberEmails() separately, mock the return here
        EmailSender spySender = Mockito.spy(sender);
        List<String> emailList = new ArrayList<>();
        emailList.add("fakeaddress@gmail.com");
        doReturn(emailList).when(spySender).getMemberEmails();
        // Don't actually send an email when running the test, mock these operations
        JavaMailSender fakeSender = mock(JavaMailSender.class);
        when(fakeSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
        doNothing().when(fakeSender).send(any(MimeMessage.class));
        boolean retVal = spySender.sendEmail("subject", "me@icloud.com", "test message", fakeSender);
        assertTrue(retVal);
    }

    @Test
    void getMemberEmailsTest(){
        List<Member> members = new ArrayList<>();
        Member toAdd = new Member();
        toAdd.setEmail("me@icloud.com");
        members.add(toAdd);
        when(controller.getMembers()).thenReturn(members);
        List<String> retVal = sender.getMemberEmails();
        assertEquals("me@icloud.com", retVal.get(0));
    }
}
