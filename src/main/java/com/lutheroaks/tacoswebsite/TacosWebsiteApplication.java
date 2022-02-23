package com.lutheroaks.tacoswebsite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TacosWebsiteApplication {
	
	@Autowired
	private MemberRepo repository;

	@PostMapping("/product")
	public Member addMember(@RequestBody Member member) {
		return repository.save(member);
	}

	@GetMapping("/products")
	public List<Member> getMembers() {
		return repository.findAll();
	}

	
	public static void main(String[] args) {
		SpringApplication.run(TacosWebsiteApplication.class, args);
	}

}
