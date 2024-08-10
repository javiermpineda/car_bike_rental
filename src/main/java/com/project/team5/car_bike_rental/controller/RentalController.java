package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.Rental;
import com.project.team5.car_bike_rental.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

        if (userProfile == null) {
            System.out.println("UserProfile is null");
            return false; // O maneja este caso de acuerdo a tus necesidades
        }

        System.out.println("userProfile.getCardNumber()" + userProfile.getCardNumber());
        System.out.println("userProfile.getCardCvv()" + userProfile.getCardCvv());
        System.out.println("userProfile.getCardExpiryDate()" + userProfile.getCardExpiryDate());

        return userProfile.getCardNumber() != null && !userProfile.getCardNumber().isEmpty()
                && userProfile.getCardCvv() != null && !userProfile.getCardCvv().isEmpty()
                && userProfile.getCardExpiryDate() != null;
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

    @PostMapping("/{id}/complete")
    public void completeRental(@PathVariable Long id, @RequestBody Rental rental) {
        Rental existingRental = rentalService.getRentalById(id);
        if (existingRental == null) {
            throw new RuntimeException("Rental not found");
        }

        if (rental.getReturnDate() != null) {
            // Convertir Date a LocalDate
            LocalDate returnDate = rental.getReturnDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Sumar un día
            LocalDate adjustedReturnDate = returnDate.plusDays(1);

            // Convertir LocalDate a Date
            Date adjustedReturnDateAsDate = Date.from(adjustedReturnDate
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());

            existingRental.setReturnDate(adjustedReturnDateAsDate);
        }

        // Actualizar el total y returnDate
        existingRental.setTotal(rental.getTotal());
        System.out.println("Received rental with returnDate: " + existingRental.getReturnDate());
        //existingRental.setReturnDate(rental.getReturnDate()); // Fecha actual
        //System.out.println("rental.getReturnDate(): " + rental.getReturnDate());
        existingRental.setState(rental.getState());

        rentalService.saveRental(existingRental);
    }

}