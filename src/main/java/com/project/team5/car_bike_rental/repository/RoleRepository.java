package com.project.team5.car_bike_rental.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.team5.car_bike_rental.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}