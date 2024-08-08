package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.Vehicle;
import com.project.team5.car_bike_rental.service.VehicleService;
import com.project.team5.car_bike_rental.model.User;
import com.project.team5.car_bike_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;

@Controller
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @GetMapping("/cars")
    public String getCars(Model model) {
        List<Vehicle> cars = vehicleService.findVehiclesByType("car");
        model.addAttribute("vehicles", cars);
        return "cars";
    }

    @GetMapping("/bikes")
    public String getBikes(Model model) {
        List<Vehicle> bikes = vehicleService.findVehiclesByType("bike");
        model.addAttribute("vehicles", bikes);
        return "bikes";
    }

    @GetMapping("/available-vehicles")
    public String getAvailableVehicles(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("pick_up_location") String pickUpLocation,
            @RequestParam("drop_off_location") String dropOffLocation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            Model model,
            Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size);
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Page<Vehicle> vehiclePage = vehicleService.findAvailableVehicles(start, end, pageable);
        model.addAttribute("vehicles", vehiclePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vehiclePage.getTotalPages());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pick_up_location", pickUpLocation);
        model.addAttribute("drop_off_location", dropOffLocation);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("userId", user.getId());
            }else{
                model.addAttribute("userId", -1);
            }
        }else{
            model.addAttribute("userId", -1);
        }

        return "cars";
    }
}
