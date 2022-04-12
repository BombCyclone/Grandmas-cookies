package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResidentController {

	@Autowired
    private ResidentRepo repository;

    /**
	 * Adds a new resident to the table
	 * @param firstName
	 * @param lastName
	 * @param roomNum
	 * @return
	 */
	@PostMapping("/resident")
	public String addResident(final String firstName, final String lastName, final Integer roomNum) {
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

	/**
	 * Returns a list of all residents in the table
	 * @return
	 */
	@GetMapping("/resident")
	public List<Resident> getResidents() {
		return repository.findAll();
	}
    
	/**
	 * Removes a resident from the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@DeleteMapping("/resident")
	public void deleteResident(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		int residentId = Integer.parseInt(request.getParameter("residentId"));
		repository.deleteResidentByResidentId(residentId);
		response.sendRedirect("resident");
	}
}
