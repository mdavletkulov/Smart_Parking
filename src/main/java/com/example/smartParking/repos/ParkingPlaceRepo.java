package com.example.smartParking.repos;

import com.example.smartParking.domain.ParkingPlace;
import org.springframework.data.repository.CrudRepository;

public interface ParkingPlaceRepo extends CrudRepository<ParkingPlace, Long> {
}
