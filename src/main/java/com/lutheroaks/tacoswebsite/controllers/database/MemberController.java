package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;
import com.lutheroaks.tacoswebsite.entities.member.MemberService;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	
	@Autowired
	private MemberRepo repository;

	@Autowired
	private MemberService service;

	@Autowired
	private AuthenticatedDetails authenticatedDetails;

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
	 * Updates member fname, lname, and email
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PutMapping("/member-details")
	public void updateMemberDetails(final HttpServletRequest request, 
	final HttpServletResponse response) throws IOException {
		service.updateMemberDetails(request, response);
	}

	/**
	 * Updates member password
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PutMapping("/member-password")
	public void updatePassword(final HttpServletRequest request, 
	final HttpServletResponse response) throws IOException {
		service.updatePassword(request, response);
	}

	/**
	 * Returns a list of all Tacos members
	 * @return
	 */
	@GetMapping("/members")
	public List<Member> getMembers() {
		return repository.findAll();
	}
	
	/**
	 * Returns the currently logged in member's details
	 * @param request
	 * @return
	 */
	@GetMapping("/member")
	public Member getLoggedInMemberDetails(final HttpServletRequest request) {
		return authenticatedDetails.getLoggedInMember(request);
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
