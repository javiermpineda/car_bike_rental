package com.project.team5.car_bike_rental.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.project.team5.car_bike_rental.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
}