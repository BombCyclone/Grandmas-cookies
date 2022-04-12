package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.bio.Bio;
import com.lutheroaks.tacoswebsite.entities.bio.BioRepo;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BioController {

    // for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(BioController.class);

    @Autowired
    private BioRepo repository;

    @Autowired
    private MemberRepo memberRepo;

    /**
     * Adds a new Member Bio to the table
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/bio")
    public void addBio(final HttpServletRequest request, 
    final HttpServletResponse response){
        try{
            // get the parameters from the request
            String major = request.getParameter("major");
            String hometown = request.getParameter("hometown");
            String backgroundInfo = request.getParameter("background");
            byte[] profilePicture = null;
            // get the member based on their login credentials
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();

            // create the new bio object and set its attributes
            Bio toAdd = new Bio();
            Member member = memberRepo.findMemberByEmail(userName);
            toAdd.setMember(member);
            toAdd.setMajor(major);
            toAdd.setHometown(hometown);
            toAdd.setBackgroundInfo(backgroundInfo);
            toAdd.setProfilePicture(profilePicture);
            // save the new Bio
            repository.save(toAdd);
        }catch(Exception e){
            logger.error("something went wrong while creating a bio: ", e);
        }
    }

    /**
     * Returns a particular member's bio
     * @return
     */
    @GetMapping("/bio")
    public Bio getBio(final HttpServletRequest request) {
        try{
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();
            Member member = memberRepo.findMemberByEmail(userName);
            return repository.findBioByMember(member);
        }catch(Exception e){
            logger.error("an exception occurred while retrieving a bio: ", e);
            return null;
        }
    }

    /**
     * Deletes a specified bio
     * @param request
     * @param response
     * @throws IOException
     */
    @Transactional
    @DeleteMapping("/bio")
    public void deleteBio(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		int bioId = Integer.parseInt(request.getParameter("bioId"));
		repository.deleteBioById(bioId);
		response.sendRedirect("index");
	}
    
}
