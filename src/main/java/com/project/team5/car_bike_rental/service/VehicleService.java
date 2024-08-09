package com.project.team5.car_bike_rental.service;

import com.project.team5.car_bike_rental.model.Vehicle;
import com.project.team5.car_bike_rental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findVehiclesByType(String type) {
        return vehicleRepository.findByType(type);
    }

    public Page<Vehicle> findAvailableVehicles(Date startDate, Date endDate, Pageable pageable) {
        return vehicleRepository.findAvailableVehicles(startDate, endDate, pageable);
    }
}
