package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.Owner;
import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLotSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotSystemRepository extends JpaRepository<ParkingLotSystem,Long> {

    List<ParkingLotSystem> findAllByOwner(Owner owner);

}
