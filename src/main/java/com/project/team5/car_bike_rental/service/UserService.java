package com.project.team5.car_bike_rental.service;

import com.project.team5.car_bike_rental.model.Role;
import com.project.team5.car_bike_rental.model.User;
import com.project.team5.car_bike_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        logger.info("Saving user: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Ensure the user is enabled by default

        // Assign roles
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        userRole.setUser(user);

        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
