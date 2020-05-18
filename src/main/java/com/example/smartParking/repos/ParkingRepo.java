package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Parking;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRepo extends CrudRepository<Parking, Long> {
}
