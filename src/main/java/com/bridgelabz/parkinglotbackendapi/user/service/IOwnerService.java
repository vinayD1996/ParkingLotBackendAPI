package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.OwnerDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.ParkingLotDto;


public interface IOwnerService {

    Response ownerRegister(OwnerDto ownerDto) throws UserException;

    Response ownerLogin(LoginDto loginDto) throws UserException;

    Response ownerValidateEmailId(String token);

    Response createParkingLot(ParkingLotDto parkingLotDto) throws UserException;
}
