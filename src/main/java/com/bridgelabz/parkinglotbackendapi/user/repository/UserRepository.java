package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.User;

import java.util.Optional;

public interface UserRepository {

     Optional<User> findByEmailId(String emailId);
}
