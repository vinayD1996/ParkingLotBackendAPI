package com.bridgelabz.parkinglotbackendapi.user.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class UserDto {

    private String firstName,lastName,mobileNumber;

    @Pattern(regexp = ("^[a-zA-Z]+([.+-]*[a-zA-Z0-9]{3})*@([a-zA-Z0-9]+)([.]?[a-z]{2,})([.]?[a-z]{2,})?$"))
    private String emailId;
    @Pattern(regexp =  ("[A-Z]+[a-zA-Z]{7,}+[0-9]+[ .!@#$%&*()?/{}:']+"))
    private String password;

    private String typeOfActor;
}
