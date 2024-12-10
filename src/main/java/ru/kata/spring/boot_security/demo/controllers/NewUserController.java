package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class NewUserController {
    private static final String REDIRECT_USERS = "redirect:/admin";
    private final UserService userService;
    private final RoleRepository roleRepository;

    public NewUserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String log(){
        return "bootstrapTemplates/arc/sign_in";
    }


    @GetMapping("admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "bootstrapTemplates/adminpage";
    }

    @GetMapping("admin/users/new")
    public String newUser(@ModelAttribute("user") User user, Model model,
                          Principal currentUser) {
        model.addAttribute("currentUser", userService.getUserByUsername(currentUser.getName()));
        model.addAttribute("roles", roleRepository.findAll());
        return "bootstrapTemplates/newuser";
    }



    @PostMapping("/admin/users")
    public String createUser(User user){
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.saveUser(user);
        return REDIRECT_USERS;
    }


    @PostMapping("/admin/users/edit/{id}")
    public String updateUser(@PathVariable("id") long id, Model model,User user) {
        userService.saveUser(user);
        return REDIRECT_USERS;
    }

    @PostMapping("admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return REDIRECT_USERS;
    }




    @GetMapping("/user")
    public String user2(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "bootstrapTemplates/user";
    }


}
