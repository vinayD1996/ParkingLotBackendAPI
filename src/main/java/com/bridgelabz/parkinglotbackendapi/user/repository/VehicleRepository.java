package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
