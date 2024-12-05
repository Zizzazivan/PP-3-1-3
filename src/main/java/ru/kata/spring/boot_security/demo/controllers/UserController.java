package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.configs.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private static final String REDIRECT_USERS = "redirect:/admin";
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("/")
    public String addSome(){

        return "index";


    }

    @GetMapping("/user")
    public String user(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "user";

    }
    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("admin/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-create-form";
    }



    @PostMapping("/user-create")
    public String createUser(User user){
        userService.saveUser(user);
        return REDIRECT_USERS;
    }

    @GetMapping("/admin/update")
    public String updateForm(@RequestParam("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-update-form";
    }

    @PostMapping("/user-update")
    public String updateUser(User user) {
        userService.saveUser(user);
        return REDIRECT_USERS;
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return REDIRECT_USERS;
    }




}
