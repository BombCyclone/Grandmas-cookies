package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

@RestController
public class MemberController {
	
	private MemberRepo repository;

	@Autowired
	public MemberController(MemberRepo repository){
		this.repository = repository;
	}

	@PostMapping("/member")
	public void addMember(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		}
		// if user already exists or something went wrong
		else{
			response.sendRedirect("error");
		}
	}
	// this method returns a list of all rows in the member table
	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}
	
}
