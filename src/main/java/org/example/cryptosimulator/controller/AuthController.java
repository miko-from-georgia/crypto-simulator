package org.example.cryptosimulator.controller;

import org.example.cryptosimulator.dto.RegistrationDto;
import org.example.cryptosimulator.service.AuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute RegistrationDto registrationDto) {
        authorizationService.registerNewClient(registrationDto);
        return "redirect:/login?registered=true";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
