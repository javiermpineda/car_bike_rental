package com.project.team5.car_bike_rental.repository;

import com.project.team5.car_bike_rental.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByType(String type);

    @Query("SELECT v FROM Vehicle v WHERE v.id NOT IN " +
            "(SELECT r.vehicle.id FROM Rental r WHERE " +
            "(:startDate BETWEEN r.rentalDate AND r.returnDate " +
            "OR :endDate BETWEEN r.rentalDate AND r.returnDate " +
            "OR :startDate <= r.rentalDate AND :endDate >= r.returnDate))")
    Page<Vehicle> findAvailableVehicles(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
