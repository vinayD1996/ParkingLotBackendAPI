package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {


}
