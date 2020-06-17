package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLot;
import com.bridgelabz.parkinglotbackendapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {

    ParkingLot findByAttendantName(String attendantName);
}
