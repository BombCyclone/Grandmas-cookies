package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.member.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	
	@Autowired
	private MemberRepo repository;

	@Autowired
	private MemberService service;

	/**
	 * Adds a new Tacos member to the table
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/member")
	public void addMember(final HttpServletRequest request, 
						final HttpServletResponse response) throws IOException {
		service.createMember(request, response);
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
	public void deleteMember(final HttpServletRequest request) {
		service.removeMember(request);
	}
	
}
