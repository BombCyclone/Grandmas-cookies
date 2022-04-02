package com.lutheroaks.tacoswebsite.helper_utils;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lutheroaks.tacoswebsite.controllers.database.MemberController;
import com.lutheroaks.tacoswebsite.member.Member;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class EmailSender {
	
	@Autowired
	private MemberController controller;

    // for logging information to console
	Logger logger = org.slf4j.LoggerFactory.getLogger(EmailSender.class);
    
    public boolean sendEmail(String subject, String address, String message, JavaMailSender mailSender) throws MessagingException{
        try{
        	// create the mimeMessage object to be sent
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			// set the email message parameters
			List<String> addressList = getMemberEmails();
			if (address != null){
				addressList.add(address);
			}
			messageHelper.setFrom("tacosemailservice@gmail.com");
			messageHelper.setSubject(subject);
			messageHelper.setText(message);
			// send the email to the list
			for(String emailAddress : addressList){
				messageHelper.setTo(emailAddress);
				mailSender.send(mimeMessage);
			}
            return true;
        }
        catch(Exception e){
            logger.error("An error occurred while sending an email: ", e);
            return false;
        }
    }

	// gets list of all member emails for sending out notifications
	public List<String> getMemberEmails(){
		List<Member> members = controller.getMembers();
		List<String> emails = new ArrayList<>();
		for(Member member : members){
			emails.add(member.getEmail());
		}
		return emails;
	}
    
}
