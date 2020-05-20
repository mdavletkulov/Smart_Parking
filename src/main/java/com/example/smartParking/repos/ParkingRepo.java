package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Parking;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParkingRepo extends CrudRepository<Parking, Long> {

    Optional<Parking> findByDescription(String description);
}
