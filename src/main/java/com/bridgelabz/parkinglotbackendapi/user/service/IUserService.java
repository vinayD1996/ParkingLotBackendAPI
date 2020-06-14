package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;

public interface IUserService {
    Response register(UserDto userDto) throws UserException;

    Response login(LoginDto loginDto) throws UserException;

    Response validateEmailId(String token) throws UserException;
}
