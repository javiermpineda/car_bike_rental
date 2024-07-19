package com.project.team5.car_bike_rental.service;

import com.project.team5.car_bike_rental.model.Vehicle;
import com.project.team5.car_bike_rental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findVehiclesByType(String type) {
        return vehicleRepository.findByType(type);
    }
}
