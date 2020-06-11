package com.bridgelabz.parkinglotbackendapi.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;


@Getter
@Setter
@ToString
public class OwnerDto {


    private String firstName;
    private String lastName;

//    @Pattern(regexp = ("^[a-zA-Z]+([.+-]{0,}[a-zA-Z0-9]{3})*@([a-zA-Z0-9]+)([.]?[a-z]{2,})([.]?[a-z]{2,})?$"))
    private String emailId;
//    @Pattern(regexp =  ("([a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-])"))
    private String password;


    private String mobileNumber;

}
