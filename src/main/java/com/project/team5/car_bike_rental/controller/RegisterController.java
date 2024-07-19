package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.User;
import com.project.team5.car_bike_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, BindingResult result, Model model) {

        if (!user.getPassword().equals(user.getRePassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        userService.saveUser(user);
        model.addAttribute("message", "User registered successfully");
        return "redirect:/login";
    }
}
