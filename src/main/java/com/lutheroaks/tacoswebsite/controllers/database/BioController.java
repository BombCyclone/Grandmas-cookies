package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.bio.Bio;
import com.lutheroaks.tacoswebsite.entities.bio.BioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BioController {

    @Autowired
    private BioService service;

    /**
     * Create a Bio
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/bio")
    public void addBio(final HttpServletRequest request, 
    final HttpServletResponse response) throws IOException{
        service.createBio(request, response);
    }

    /**
     * Retrieve the logged in member's bio
     * @param request
     * @return
     */
    @GetMapping("/bio")
    public Bio getBio(final HttpServletRequest request) {
        return service.retrieveBio(request);
    }

    /**
     * Delete the logged in member's bio
     * @param request
     * @param response
     * @throws IOException
     */
    @Transactional
    @DeleteMapping("/bio")
    public void deleteBio(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        service.removeBio(request, response);
	}
    /** 
    *updates a specified bio
    * 
    * @param request
    * @param response
    * @throws IOException
    */
    @PutMapping("/bio")
    public void updateABio(final HttpServletRequest request, final HttpServletResponse response){
        service.updateBio(request, response);
    }
    
}
