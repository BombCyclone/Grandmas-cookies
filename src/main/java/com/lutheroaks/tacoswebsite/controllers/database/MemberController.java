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
		// if empty, add to table and refresh page
		if (repository.findMemberByEmail(email).isEmpty()) {
			Member toAdd = new Member();
			toAdd.setFirstName(fName);
			toAdd.setLastName(lName);
			toAdd.setEmail(email);
			toAdd.setPassword("tacos");
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
