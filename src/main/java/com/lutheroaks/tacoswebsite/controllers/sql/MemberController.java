package com.lutheroaks.tacoswebsite.controllers.sql;

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

	@PostMapping("/member")
	public String addMember(String fname, String lname, String email) {
		Member toAdd = new Member(fname, lname, email);
		repository.save(toAdd);
		return "A new member was added!";
	}

	@GetMapping("/member")
	public List<Member> getMembers() {
		return repository.findAll();
	}
}
