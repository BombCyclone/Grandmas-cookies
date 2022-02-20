package com.lutheroaks.tacoswebsite.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// This class returns a custom 404 error page when attempting to reach an unmapped address
@Controller
public class RoutingErrorController implements ErrorController  {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
