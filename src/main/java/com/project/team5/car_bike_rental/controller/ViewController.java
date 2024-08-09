package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.service.RentalService;
import com.project.team5.car_bike_rental.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;




import com.project.team5.car_bike_rental.model.Rental;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ViewController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/history")
    public String getRentalsByUserId(@PathVariable Long userId, Model model) {
        List<com.project.team5.car_bike_rental.model.Rental> rentals = rentalService.getRentalsByUserId(userId);
        model.addAttribute("rentals", rentals);
        return "rentalList";
    }

    @GetMapping("/history")
    public String getRentalsByUserId(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Get the username of the currently logged-in user

        // You need to implement a method to fetch user by username and get userId
        Long userId = getUserIdByUsername(username); 

        List<com.project.team5.car_bike_rental.model.Rental> rentals = rentalService.getRentalsByUserId(userId);
        model.addAttribute("rentals", rentals);
        return "rentalList";
    }

    private Long getUserIdByUsername(String username) {
        
        // Implement this method to retrieve userId based on the username
        // For example, use a UserService or UserRepository to fetch the user
        // and return the userId
        return userService.findByUsername(username).getId();
    }
}
