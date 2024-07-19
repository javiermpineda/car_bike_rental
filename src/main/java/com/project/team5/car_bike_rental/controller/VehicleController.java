package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.Vehicle;
import com.project.team5.car_bike_rental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/cars")
    public String getCars(Model model) {
        List<Vehicle> cars = vehicleRepository.findByType("car");
        model.addAttribute("vehicles", cars);
        return "cars";
    }

    @GetMapping("/bikes")
    public String getBikes(Model model) {
        List<Vehicle> bikes = vehicleRepository.findByType("bike");
        model.addAttribute("vehicles", bikes);
        return "bikes";
    }
}
