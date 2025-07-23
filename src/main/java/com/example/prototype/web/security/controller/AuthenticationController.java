package com.example.prototype.web.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    /**
     * 認証用のログイン画面表示
     * @return
     */
    @GetMapping(value = "login")
    public String login() {
        return "security/login";
    }
}
