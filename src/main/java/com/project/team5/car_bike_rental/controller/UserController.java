package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.User;
import com.project.team5.car_bike_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/{username}")
    public String getUserProfile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/edit/{username}")
    public String editUserProfile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/update")
    public String updateUserProfile(User user, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null) {
            return "error/404";
        }
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setEnabled(user.isEnabled());
        userService.saveUser(existingUser);
        return "redirect:/user/" + user.getUsername();
    }
}
