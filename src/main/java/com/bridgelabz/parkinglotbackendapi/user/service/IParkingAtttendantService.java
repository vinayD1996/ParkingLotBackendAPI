package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.VehicleDto;

public interface IParkingAtttendantService {

    Response parkVehicle(VehicleDto vehicleDto);

    Response unParkVehicle(String vehiclePlateNumber);
}
