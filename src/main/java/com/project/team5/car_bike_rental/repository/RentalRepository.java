package com.project.team5.car_bike_rental.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.team5.car_bike_rental.model.Rental;


public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
}
