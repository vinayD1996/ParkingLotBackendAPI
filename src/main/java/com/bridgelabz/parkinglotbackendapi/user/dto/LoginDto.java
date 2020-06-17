package com.bridgelabz.parkinglotbackendapi.user.dto;

import lombok.Data;


@Data
public class LoginDto {

    private String emailId;
    private String password;
}
