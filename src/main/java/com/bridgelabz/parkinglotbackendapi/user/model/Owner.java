package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ownerId")
    private Long ownerId;

    private String firstName,lastName,emailId;
    private String password,mobileNumber;
    private int numberOfLotSytems;
    private boolean isVerify;
    private LocalDateTime registeredDateAndTime = LocalDateTime.now();

    @OneToMany(mappedBy = "owner")
    public List<ParkingLotSystem> parkingLotSystems = new ArrayList<>();

    @OneToMany(cascade =CascadeType.ALL,mappedBy = "owner")
    public List<ParkingLot> parkingLots = new ArrayList<>();

    public void addParkingLotSystems(ParkingLotSystem parkingLotSystem) {
        this.parkingLotSystems.add(parkingLotSystem);
    }

}
