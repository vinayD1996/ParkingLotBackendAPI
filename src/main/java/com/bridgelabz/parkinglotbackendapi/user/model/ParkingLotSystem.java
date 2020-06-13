package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ParkingLotSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingLotSystemId;

    private int numberOfLots;


    @ManyToOne
    private Owner owner;

    public ParkingLotSystem(int parkingLotSystemId) {
        this.parkingLotSystemId = parkingLotSystemId;
    }

    @OneToMany(mappedBy = "parkingLotSystem")
    private List<ParkingLot> parkingLots = new ArrayList<>();

    public void addParkingLot (ParkingLot parkingLot) {

        this.parkingLots.add(parkingLot);
    }

}
