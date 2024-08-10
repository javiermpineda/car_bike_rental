package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.User;
import com.project.team5.car_bike_rental.model.Vehicle;
import com.project.team5.car_bike_rental.service.UserService;
import com.project.team5.car_bike_rental.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @GetMapping("/vehicles")
    public String manageVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "admin/manageVehicles";
    }

    @GetMapping("/vehicles/add")
    public String addVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "admin/addVehicle";
    }

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";



    @PostMapping("/vehicles/add")
    public String addVehicle(@RequestParam("make") String make,
                             @RequestParam("model") String model,
                             @RequestParam("price_per_day") Double pricePerDay,
                             @RequestParam("type") String type,
                             @RequestParam("image") MultipartFile imageFile,
                             @RequestParam("url") String url,
                             @RequestParam("year") Integer year) {

        // Save the file to the static/images directory
        if (!imageFile.isEmpty()) {
            try {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + imageFile.getOriginalFilename());
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setPrice_per_day(pricePerDay);
        vehicle.setType(type);
        vehicle.setImage("/images/" + imageFile.getOriginalFilename()); // Save the image path
        vehicle.setUrl(url);
        vehicle.setYear(year);

        vehicleService.addVehicle(vehicle);
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/manageUsers";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        long vehicleCount = vehicleService.getAllVehicles().size();
        long userCount = userService.getAllUsers().size();
        model.addAttribute("vehicleCount", vehicleCount);
        model.addAttribute("userCount", userCount);
        return "admin/dashboard";
    }
}
