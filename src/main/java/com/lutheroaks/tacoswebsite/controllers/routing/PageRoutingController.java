package com.lutheroaks.tacoswebsite.controllers.routing;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// This class only contains logic necessary for page routing
@Controller
public final class PageRoutingController {

	@GetMapping("/index")
	public String viewIndex() {
		return "index";
	}

	@GetMapping("/ticket-form")
	public String viewTicketForm() {
		return "ticket-form";
	}

	@GetMapping("/active-tickets")
	public String viewActiveTickets() {
		return "active-tickets";
	}

	@GetMapping("/member-table")
	public String viewArchivedTickets() {
		return "member-table";
	}

	@GetMapping("/resident-table")
	public String viewResident() {
		return "resident-table";
	}

	@GetMapping("/profile")
	public String viewProfile() {
		return "profile";
	}

	@GetMapping("/faq")
	public String viewFaq() {
		return "faq";
	}

	@GetMapping("/contactus")
	public String viewContactUs() {
		return "contactus";
	}

	@GetMapping("/register")
	public String viewRegistration() {
		return "register";
	}

	@GetMapping("/login")
	public String viewLogin() {
		return "login";
	}	



}
