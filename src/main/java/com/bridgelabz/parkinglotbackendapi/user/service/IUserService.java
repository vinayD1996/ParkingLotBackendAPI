package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.response.ResponseToken;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import com.bridgelabz.parkinglotbackendapi.user.model.User;

import java.util.Optional;


public interface IUserService {
    Response register(UserDto userDto) throws UserException;

    Response login(LoginDto loginDto) throws UserException;

    Response validateEmailId(String token) throws UserException;
}
