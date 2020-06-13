package com.bridgelabz.parkinglotbackendapi.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {


    private String vehicleModel, vehicleColor,vehiclePlateNumber, emailId;;

    public VehicleDto(String vehicleModel, String vehicleColor, String vehiclePlateNumber) {
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.vehiclePlateNumber = vehiclePlateNumber;
    }
}
