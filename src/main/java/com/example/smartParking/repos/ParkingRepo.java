package com.example.smartParking.repos;

import com.example.smartParking.domain.Parking;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRepo extends CrudRepository<Parking, Long> {
}
