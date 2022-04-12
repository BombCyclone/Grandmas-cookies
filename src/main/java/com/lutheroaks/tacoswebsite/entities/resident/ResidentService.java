package com.lutheroaks.tacoswebsite.entities.resident;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class ResidentService {

    @Autowired
    private ResidentRepo repository;
    
    /**
     * Creates and saves a new resident if they do not already exist in the table
     * @param firstName
     * @param lastName
     * @param roomNum */
    public void createResident(final String firstName, final String lastName, final Integer roomNum) {
        //check for duplicate resident with matching first and last name
		if (repository.findResidentByName(firstName.toUpperCase(), lastName.toUpperCase()).isEmpty()) {
			Resident toAdd = new Resident();
			toAdd.setFirstName(firstName);
			toAdd.setLastName(lastName);
			toAdd.setRoomNum(roomNum);
			repository.save(toAdd);
		}
    }

	/**
	 * Removes a resident from the table
	 * @param request
	 */
    public void removeResident(final HttpServletRequest request) {
        int residentId = Integer.parseInt(request.getParameter("residentId"));
		repository.deleteResidentByResidentId(residentId);
    }
}
