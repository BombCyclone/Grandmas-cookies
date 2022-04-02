package com.lutheroaks.tacoswebsite.helper_utils;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketHelper {

    public Resident findResident(String fname, String lname, int roomNum, ResidentRepo repo){
        List<Resident> matchingResidents = repo.findResidentByName(fname, lname);
		Resident ticketResident;
		if(matchingResidents.isEmpty()){
            ticketResident = new Resident();
			// no resident was found, so create new resident
			ticketResident.setFirstName(fname);
			ticketResident.setLastName(lname);
			ticketResident.setRoomNum(roomNum);
			ticketResident.setAssociatedTickets(new ArrayList<>());
		}
		else{
			// set the resident to use to be the match from the query
			ticketResident = matchingResidents.get(0);
		}
        return ticketResident;	
    }
}
