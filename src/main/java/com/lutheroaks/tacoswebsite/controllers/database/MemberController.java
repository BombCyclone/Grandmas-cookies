package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

@RestController
public class MemberController {

	// for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberRepo repository;

	/**
	 * Adds a new Tacos member to the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/member")
	public void addMember(final HttpServletRequest request, 
						final HttpServletResponse response) throws IOException {
		try{
			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String email = request.getParameter("email");
			// if no member is found in the table by the given email, we can create the new member
			if (repository.findMemberByEmail(email) == null) {
				Member toAdd = new Member();
				toAdd.setFirstName(fName);
				toAdd.setLastName(lName);
				toAdd.setEmail(email);
				// this is the encrypted form of the password: "tacos"
				toAdd.setPassword("$2a$10$ga75bkq0QgV63EvFY1iX6.6L0Y7wxdi2yOLAlqklRcmZutMu2ohJy");
				toAdd.setRole("ROLE_ADMIN");
				toAdd.setEnabled(true);
				repository.save(toAdd);
				response.sendRedirect("member-table");
			} else{
				if(logger.isInfoEnabled() && email != null){
					logger.info(String.format("An existing user with the email address: " + 
										"%s was found in the database.", email));
				}
				response.sendRedirect("error");
			}
		} catch(IOException e){
			logger.error("An error occurred while adding a Member: ", e);
            response.sendRedirect("error");
		}
	}
	
	/**
	 * Returns a list of all Tacos members
	 * @return
	 */
	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}

	/**
	 * Deletes the member specified
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@DeleteMapping("/member")
	public void deleteMember(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		repository.deleteMemberById(memberId);
		response.sendRedirect("member");
	}
	
}
