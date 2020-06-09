package com.bridgelabz.parkinglotbackendapi.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class LoginDto {

    private String emailId;
    private String password;
}
