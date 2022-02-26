package com.lutheroaks.tacoswebsite.controllers.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageRoutingControllerTest {

	@Autowired
	private PageRoutingController controller;
    
    @Test
	public void indexPageIsReturned() {
		String retVal = controller.viewIndex();
		assertEquals("index", retVal);
	}

	@Test
	public void ticketFormPageIsReturned() {
		String retVal = controller.viewTicketForm();
		assertEquals("ticket-form", retVal);
	}

	@Test
	public void activeTicketsPageIsReturned() {
		String retVal = controller.viewActiveTickets();
		assertEquals("active-tickets", retVal);
	}

	@Test
	public void archivedTicketsPageIsReturned() {
		String retVal = controller.viewArchivedTickets();
		assertEquals("archived-tickets", retVal);
	}

	@Test
	public void profilePageIsReturned() {
		String retVal = controller.viewProfile();
		assertEquals("profile", retVal);
	}

	@Test
	public void faqPageIsReturned() {
		String retVal = controller.viewFaq();
		assertEquals("faq", retVal);
	}

	@Test
	public void contactUsPageisReturned(){
		String retVal = controller.viewContactUs();
		assertEquals("contactus", retVal);
	}

	@Test
	public void registrationPageIsReturned() {
		String retVal = controller.viewRegistration();
		assertEquals("register", retVal);
	}

	@Test
	public void loginPageIsReturned() {
		String retVal = controller.viewLogin();
		assertEquals("login", retVal);
	}
}
