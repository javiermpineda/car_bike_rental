package com.project.team5.car_bike_rental.service;

import com.project.team5.car_bike_rental.model.Voucher;
import com.project.team5.car_bike_rental.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getVouchersByUserId(Long userId) {
        return voucherRepository.findByUserId(userId);
    }

}
