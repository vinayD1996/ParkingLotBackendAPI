package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table
@Component
@NoArgsConstructor
@Data
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private Long userId;

    private String firstName,lastName,emailId;
    private String password,mobileNumber;
    private boolean isVerify;
    private String typeOfActor;
    private LocalDateTime registeredDateAndTime = LocalDateTime.now();

}
