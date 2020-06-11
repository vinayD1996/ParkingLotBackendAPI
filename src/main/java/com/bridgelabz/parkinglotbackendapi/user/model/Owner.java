package com.bridgelabz.parkinglotbackendapi.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@Entity
@Table
@Component
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ownerId")
    private Long ownerId;

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String  mobileNumber;
    private boolean isVerify;
    private LocalDateTime registerDate = LocalDateTime.now();
}
