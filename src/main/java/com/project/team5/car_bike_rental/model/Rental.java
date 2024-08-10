package com.project.team5.car_bike_rental.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "rental_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rentalDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "pick_up_location")
    private String pickUpLocation;

    @Column(name = "drop_off_location")
    private String dropOffLocation;

    @Column(name = "state")
    private String state = "reserved";  // Default value

    @Column(name = "total")
    private BigDecimal total;

    // Voucher-related fields
    @Column(nullable = false)
    private String voucherCode;

    @Column(nullable = false)
    private double discountAmount;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
}
