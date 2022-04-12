package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentService;

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

	@Autowired
	private ResidentService service;

    /**
	 * Adds a new resident to the table
	 * @param firstName
	 * @param lastName
	 * @param roomNum
	 * @return
	 */
	@PostMapping("/resident")
	public void addResident(final String firstName, final String lastName,
		 	final Integer roomNum) {
		service.createResident(firstName, lastName, roomNum);
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
	 */
	@Transactional
	@DeleteMapping("/resident")
	public void deleteResident(final HttpServletRequest request) {
		service.removeResident(request);
	}
}
