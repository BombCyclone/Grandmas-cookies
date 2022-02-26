package com.lutheroaks.tacoswebsite.controllers.member;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

@RestController
public class MemberController {
	
	@Autowired
	private MemberRepo repository;

	@PostMapping("/member")
	public Member addMember(@RequestBody Member member) {
		return repository.save(member);
	}

	@GetMapping("/members")
	public List<Member> getMembers() {
		return repository.findAll();
	}
}
