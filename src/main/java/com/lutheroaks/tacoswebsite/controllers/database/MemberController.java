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
	
	@Autowired
	private MemberRepo repository;

	// this method adds a new row to the member table
	@PostMapping("/member")
	public String addMember(String fname, String lname, String email) {
		Member toAdd = new Member(fname, lname, email);
		repository.save(toAdd);
		return "A new member was added!";
	}

	// this method returns a list of all rows in the member table
	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}
}
