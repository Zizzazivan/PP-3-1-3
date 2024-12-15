package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;

@Controller
public class NewStartingController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public NewStartingController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        User user = new User();
        user.setEmail("admin");
        user.setPassword(BCrypt.hashpw("100", BCrypt.gensalt()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userService.saveUser(user);
        return "redirect:/login";

    }





}
