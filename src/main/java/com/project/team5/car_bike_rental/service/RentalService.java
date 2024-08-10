package com.project.team5.car_bike_rental.service;

import com.project.team5.car_bike_rental.model.Rental;
import com.project.team5.car_bike_rental.repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
          // Nuevo método para obtener rentas por ID de usuario
    
          public List<Rental> getRentalsByUserId(Long userId) {
            return rentalRepository.findByUserId(userId);
        }

    public void updateRentalStatus(Long id, String status) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setState(status);
        rentalRepository.save(rental);
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

}