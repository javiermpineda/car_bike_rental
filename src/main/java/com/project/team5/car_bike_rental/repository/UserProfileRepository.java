package com.project.team5.car_bike_rental.repository;

import com.project.team5.car_bike_rental.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    UserProfile findByFullName(String username);
}
