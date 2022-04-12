package com.lutheroaks.tacoswebsite.entities.member;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberService {

    // for logging information to console
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MemberService.class);
    
    @Autowired
    private MemberRepo repository;

    /**
     * Creates and saves a new Tacos Member
     * @param request
     * @param response
     * @throws IOException
     */
    public void createMember(final HttpServletRequest request, 
    final HttpServletResponse response) throws IOException {
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
    }
    
    /**
     * Deletes a specified member
     * @param request
     */
    public void removeMember(final HttpServletRequest request) {
        int memberId = Integer.parseInt(request.getParameter("memberId"));
		repository.deleteMemberById(memberId);
    }
}
