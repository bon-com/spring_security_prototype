package com.example.prototype.web.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping(value = "login")
    public String login() {
        return "security/login";
    }
}
