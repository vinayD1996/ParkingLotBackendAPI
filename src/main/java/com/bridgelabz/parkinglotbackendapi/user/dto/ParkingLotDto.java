package com.bridgelabz.parkinglotbackendapi.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingLotDto {

    private   LoginDto loginDto;

    private int numberOfLotSystems,numberOfLots;

    private int[] numberOfSlots;

    private String[] attendantName;
}
