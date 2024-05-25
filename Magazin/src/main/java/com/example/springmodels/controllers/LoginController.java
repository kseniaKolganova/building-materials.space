package com.example.springmodels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/authorization")
    public String showLoginForm() {
        return "authorization";
    }

    @GetMapping("/goToRegistration")
    public String redirectToRegistration() {
        return "redirect:/registration";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "main";
    }
}
