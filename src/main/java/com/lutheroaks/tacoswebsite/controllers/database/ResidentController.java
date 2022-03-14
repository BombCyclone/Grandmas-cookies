package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lutheroaks.tacoswebsite.resident.Resident;
import com.lutheroaks.tacoswebsite.resident.ResidentRepo;

@RestController
public class ResidentController {

    private ResidentRepo repository;

    @Autowired
    public ResidentController(ResidentRepo repository){
        this.repository = repository;
    }

    // this method adds a new row to the member table
	@PostMapping("/resident")
	public String addResident(String firstName, String lastName, int roomNum) {
		//check for duplicate resident via first AND last name
		if (repository.findResidentByName(firstName, lastName).isEmpty()) {
			Resident toAdd = new Resident(firstName, lastName, roomNum);
			repository.save(toAdd);
			return "A new resident was added!";
		} else {
			return "Resident already in system";
		}
	}

	// this method returns a list of all rows in the member table
	@GetMapping("/resident")
	public List<Resident> getResidents() {
		return repository.findAll();
	}
    
}
