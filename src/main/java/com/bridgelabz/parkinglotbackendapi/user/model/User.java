package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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
