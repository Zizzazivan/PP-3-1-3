package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
@Controller
public class UserController {
    UserService userService;

    UserController (UserService userService) {
        this.userService = userService;

    }
    @GetMapping("/user")
    public String user2(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "bootstrapTemplates/user";
    }
}
