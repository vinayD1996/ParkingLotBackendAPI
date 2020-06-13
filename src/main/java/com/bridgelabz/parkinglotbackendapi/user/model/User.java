package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@Entity
@Table
@Component
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private Long userId;

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String mobileNumber;
    private boolean isVerify;
    private String typeOfActor;
    private LocalDateTime registeredDateAndTime = LocalDateTime.now();

}
