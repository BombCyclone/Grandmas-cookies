package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

@RestController
public class ResidentController {

	// for logging information to console
	Logger logger = org.slf4j.LoggerFactory.getLogger(ResidentController.class);

    private ResidentRepo repository;

    @Autowired
    public ResidentController(ResidentRepo repository){
        this.repository = repository;
    }

    // this method adds a new row to the member table
	@PostMapping("/resident")
	public String addResident(String firstName, String lastName, Integer roomNum) {
		try{
			//check for duplicate resident with matching first and last name
			if (repository.findResidentByName(firstName.toUpperCase(), lastName.toUpperCase()).isEmpty()) {
				Resident toAdd = new Resident();
				toAdd.setFirstName(firstName);
				toAdd.setLastName(lastName);
				toAdd.setRoomNum(roomNum);
				repository.save(toAdd);
				return "A new resident was added!";
			} else {
				return "Resident already in system";
			}
		}
		catch(Exception e){
			logger.error("An error occurred while adding a Resident: ", e);
			return "An exception occurred";
		}
	}

	// this method returns a list of all rows in the member table
	@GetMapping("/resident")
	public List<Resident> getResidents() {
		return repository.findAll();
	}
    
}
