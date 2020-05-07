package com.example.smartParking.repos;

import com.example.smartParking.domain.ParkingEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ParkingEventRepo extends CrudRepository<ParkingEvent, Long> {

    @Query(
            value = "Select * FROM parking_event WHERE place_id = ?1 and end_time IS NULL",
            nativeQuery = true)
    ParkingEvent findActiveParkingEvent(int parkingPlaceId);
}
