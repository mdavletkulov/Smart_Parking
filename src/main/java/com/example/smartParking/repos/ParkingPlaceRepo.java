package com.example.smartParking.repos;

import com.example.smartParking.domain.ParkingPlace;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingPlaceRepo extends CrudRepository<ParkingPlace, Long> {

    List<ParkingPlace> findByParkingId(int parkingId);

}
