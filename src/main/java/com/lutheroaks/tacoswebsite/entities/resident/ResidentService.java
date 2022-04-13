package com.lutheroaks.tacoswebsite.entities.resident;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ResidentService {

    @Autowired
    private ResidentRepo repository;
// for logging information to console
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ResidentService.class);
    
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

    /**
     * updates a redsident
     * @param request
     */
    public void updateResident(final HttpServletRequest request){
        int Id = Integer.parseInt(request.getParameter("Id"));
        Resident tempRes = repository.findResidentById(Id);
        if(tempRes!=null){
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int roomNum = Integer.parseInt(request.getParameter("roomNumber"));
            tempRes.setFirstName(firstName);
            tempRes.setLastName(lastName);
            tempRes.setRoomNum(roomNum);
            repository.save(tempRes);
        }else{
            logger.info("cant update resident because they did not exist");          
        }
    }
}
