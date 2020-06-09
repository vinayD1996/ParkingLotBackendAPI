package com.bridgelabz.parkinglotbackendapi.user.service;


import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.response.ResponseToken;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;

public class IUserServiceImpl  implements  IUserService{

    @Override
    public Response register(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) {
        return null;
    }

    @Override
    public Response validateEmailId(String token) {
        return null;
    }
}
