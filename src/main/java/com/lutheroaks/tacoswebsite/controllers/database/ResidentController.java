package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.resident.Resident;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentRepo;
import com.lutheroaks.tacoswebsite.entities.resident.ResidentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
     * @throws IOException
	 */
	@PostMapping("/resident")
	public void addResident(final HttpServletRequest request,final HttpServletResponse response) throws IOException {
		service.createResident(request,response);
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
	/**
	 * updates a resident
	 * @param request
	 */
	@PutMapping("/resident")
	public void updateResident(final HttpServletRequest request){
		service.updateResident(request);
	}

}
