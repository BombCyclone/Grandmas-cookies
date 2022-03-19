package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public String addMember(HttpServletRequest request){
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		if (repository.findMemberByEmail(email).isEmpty()) {
			Member toAdd = new Member();
			toAdd.setFirstName(fName);
			toAdd.setLastName(lName);
			toAdd.setEmail(email);
			repository.save(toAdd);
			return "a new member was added!";
		}else{
			return "Member already in system";
		}
	}
	// this method returns a list of all rows in the member table
	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}
	
}
