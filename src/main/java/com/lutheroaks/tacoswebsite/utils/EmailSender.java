package com.lutheroaks.tacoswebsite.utils;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lutheroaks.tacoswebsite.controllers.database.MemberController;
import com.lutheroaks.tacoswebsite.entities.member.Member;

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
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EmailSender.class);
    
	/**
	 * Sends an email with the specified parameters
	 * 
	 * @param subject
	 * @param address
	 * @param message
	 * @param mailSender
	 * @return boolean
	 * @throws MessagingException
	 */
    public boolean sendEmail(final String subject, final String address, final String message,
							final JavaMailSender mailSender) throws MessagingException {
        try{
        	// create the mimeMessage object to be sent
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

			// set the email message parameters
			List<String> addressList = getMemberEmails();
			// send email to all Tacos members via BCC
			for(String email : addressList){
				messageHelper.addBcc(email);
			}
			// send email copy to requester if address is provided
			if (address != null && !"".equals(address)){
				messageHelper.setTo(address);
			}
			messageHelper.setFrom("tacosemailservice@gmail.com");
			messageHelper.setSubject(subject);
			messageHelper.setText(message);
			// send the email to the list
			mailSender.send(mimeMessage);
            return true;
        } catch(Exception e){
            logger.error("An error occurred while sending an email: ", e);
            return false;
        }
    }

	/**
	 * Returns a list containing the email address for every Tacos member
	 * @return List<String>
	 */
	public List<String> getMemberEmails(){
		List<Member> members = controller.getMembers();
		List<String> emails = new ArrayList<>();
		for(Member member : members){
			emails.add(member.getEmail());
		}
		return emails;
	}
    
}
