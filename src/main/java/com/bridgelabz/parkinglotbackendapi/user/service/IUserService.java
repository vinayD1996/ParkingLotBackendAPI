package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.response.ResponseToken;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    Response register(UserDto userDto);

    ResponseToken login(LoginDto loginDto);

    Response validateEmailId(String token);
}
