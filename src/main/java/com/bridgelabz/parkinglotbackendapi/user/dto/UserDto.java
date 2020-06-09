package com.bridgelabz.parkinglotbackendapi.user.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String mobileNum;
    private String typeOfActor;
}
