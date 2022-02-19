package com.lutheroaks.tacoswebsite.controller;

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
	public void profilePageIsReturned() {
		String retVal = controller.viewProfile();
		assertEquals("profile", retVal);
	}

	@Test
	public void contactUsPageisReturned(){
		String retVal = controller.contactUs();
		assertEquals("contactus", retVal);
	}
}
