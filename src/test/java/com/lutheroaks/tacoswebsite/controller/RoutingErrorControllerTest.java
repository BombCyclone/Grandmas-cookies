package com.lutheroaks.tacoswebsite.controller;

import com.lutheroaks.tacoswebsite.controller.RoutingErrorController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoutingErrorControllerTest {

    @Autowired
    private RoutingErrorController controller;

    @Test
    public void errorPageIsReturned() {
        String retVal = controller.handleError();
        assertEquals("error", retVal);
    } 
}
