package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {

     User findByEmailId(String emailId);

     User findByUserId(Long user_id);
}
