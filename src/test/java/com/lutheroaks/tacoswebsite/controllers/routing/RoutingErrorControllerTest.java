package com.lutheroaks.tacoswebsite.controllers.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class RoutingErrorControllerTest {

    @Autowired
    private RoutingErrorController controller;

    @Test
    public void errorPageIsReturned() {
        String retVal = controller.handleError();
        assertEquals("error", retVal);
    } 
}
