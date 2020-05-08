package com.example.smartParking.repos;

import com.example.smartParking.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<Event, Long> {

    @Query(
            value = "Select * FROM parking_event WHERE place_id = ?1 and end_time IS NULL",
            nativeQuery = true)
    Event findActiveParkingEvent(Long parkingPlaceId);
}
