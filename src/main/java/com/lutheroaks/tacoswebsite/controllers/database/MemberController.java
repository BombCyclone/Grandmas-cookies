package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;
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

	// this method adds a new row to the member table
	@PostMapping("/member")
	public String addMember(String firstName, String lastName, String email) {
		//check for duplicate member by email
		if (repository.findMemberByEmail(email).isEmpty()) {
			Member toAdd = new Member(firstName, lastName, email);
			repository.save(toAdd);
			return "A new member was added!";
		} else {
			return "Member already in system";
		}
	}

	// this method returns a list of all rows in the member table
	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}
}
