package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.Rental;
import com.project.team5.car_bike_rental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.project.team5.car_bike_rental.model.UserProfile;
import com.project.team5.car_bike_rental.service.UserProfileService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/validateCardNumber")
    @ResponseBody
    public boolean validateCardNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("username " + username);

        UserProfile userProfile = userProfileService.getUserProfileByUsername(username);
        System.out.println("userProfile.getCardNumber()" + userProfile.getCardNumber());
        System.out.println("userProfile.getCardCvv()" + userProfile.getCardCvv());
        System.out.println("userProfile.getCardExpiryDate()" + userProfile.getCardExpiryDate());
        return userProfile != null && userProfile.getCardNumber() != null && !userProfile.getCardNumber().isEmpty() && userProfile.getCardCvv() != null && !userProfile.getCardCvv().isEmpty() && userProfile.getCardExpiryDate() != null;
    }

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @PostMapping
    public Rental saveRental(@RequestBody Rental rental) {
        System.out.println("Received rental request: " + rental);
        return rentalService.saveRental(rental);
    }

    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }
}