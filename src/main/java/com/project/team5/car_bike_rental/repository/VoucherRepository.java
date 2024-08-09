package com.project.team5.car_bike_rental.repository;

import com.project.team5.car_bike_rental.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByUserId(Long userId);
}
