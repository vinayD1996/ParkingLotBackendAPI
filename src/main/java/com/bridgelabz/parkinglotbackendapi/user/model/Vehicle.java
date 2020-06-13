package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;



@Entity
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vehicleId;

    private String emailId;

    private long slotNumber;

    private String vehicleModel,vehicleColor,vehiclePlateNumber;

    private LocalDateTime vehicleInTime = LocalDateTime.now();

    @ManyToOne
    private ParkingLot parkingLot;
}
