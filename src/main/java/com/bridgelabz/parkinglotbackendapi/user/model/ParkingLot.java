package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lotId;

    private long numberOfSlots,AvailableSlots;

    private String attendantName;


    private boolean isVacant;


    @ManyToOne
    private ParkingLotSystem parkingLotSystem;

    @ManyToOne
    private Owner owner;

    public ParkingLot(int lotId) {

        this.lotId = lotId;
    }


    @OneToMany(mappedBy = "parkingLot")
    private List<Vehicle> vehicleList = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {

        this.vehicleList.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {

        this.vehicleList.remove(vehicle);
    }




}
