package com.lutheroaks.tacoswebsite.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.lutheroaks.controller.AppController;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllerTests {
    
    @Test
	void indexPageLoads() {
		AppController controller = new AppController();
		String retVal = controller.viewIndex();
		assertEquals(retVal, "/index");
	}
}
