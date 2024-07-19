package com.project.team5.car_bike_rental.repository;

import com.project.team5.car_bike_rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
