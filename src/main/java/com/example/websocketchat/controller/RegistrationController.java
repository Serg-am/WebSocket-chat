package com.example.websocketchat.controller;

import com.example.websocketchat.model.UserModel;
import com.example.websocketchat.repository.UserRepository;
import com.example.websocketchat.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {


    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    public RegistrationController(UserRepository userRepository, RegistrationService registrationService) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(UserModel user, Model model) {
        UserModel existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("registrationError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        registrationService.register(user);
        return "redirect:/login"; // Перенаправление на страницу входа
    }
}
