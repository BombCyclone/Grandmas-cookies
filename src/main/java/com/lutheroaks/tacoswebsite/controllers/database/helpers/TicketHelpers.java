package com.lutheroaks.tacoswebsite.controllers.database.helpers;

import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class TicketHelpers {

    // for logging information to console
	Logger logger = org.slf4j.LoggerFactory.getLogger(TicketHelpers.class);
    
    public boolean sendEmail(String name, String email, String message, JavaMailSender mailSender) throws MessagingException{
        try{
        	// create the mimeMessage object to be sent
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			// set the email message parameters
			messageHelper.setFrom("tacosemailservice@gmail.com");
			messageHelper.setTo(email);
			messageHelper.setSubject("TACOS ticket submission confirmation email");
			messageHelper.setText("Thank you" + name + "your ticket has been submitted and will be resolved soon.\n" + "Ticket message: \n" + message);
			// send the email
			mailSender.send(mimeMessage);
            return true;
        }
        catch(Exception e){
            logger.error("An error occurred while sending an email: ", e);
            return false;
        }
    }

    public Resident findResident(String fname, String lname, int roomNum, ResidentRepo repo){
        List<Resident> matchingResidents = repo.findResidentByName(fname, lname);
		Resident ticketResident;
		if(matchingResidents.isEmpty()){
            ticketResident = new Resident();
			// no resident was found, so create new resident
			ticketResident.setFirstName(fname);
			ticketResident.setLastName(lname);
			ticketResident.setRoomNum(roomNum);
			ticketResident.setAssociatedTickets(new HashSet<>());
		}
		else{
			// set the resident to use to be the match from the query
			ticketResident = matchingResidents.get(0);
		}
        return ticketResident;	
    }
}
