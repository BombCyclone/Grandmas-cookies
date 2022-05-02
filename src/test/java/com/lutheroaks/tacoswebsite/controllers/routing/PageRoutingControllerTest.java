package com.lutheroaks.tacoswebsite.controllers.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class PageRoutingControllerTest {

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
	public void ticketViewPageIsReturned() {
		String retVal = controller.viewTicket();
		assertEquals("ticket-view", retVal);
	}

	@Test
	public void archivedTicketsPageIsReturned() {
		String retVal = controller.viewArchivedTickets();
		assertEquals("member-table", retVal);
	}

	@Test
	public void residentTablePageIsReturned() {
		String retVal = controller.viewResident();
		assertEquals("resident-table", retVal);
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
		assertEquals("member-login", retVal);
	}
}
