package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface EventRepo extends CrudRepository<Event, Long> {

    @Query(
            value = "Select * FROM parking_event WHERE place_id = ?1 and end_time IS NULL",
            nativeQuery = true)
    Event findActivePlaceEvent(Long parkingPlaceId);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN" +
                    " parking_place ON parking_event.place_id = parking_place.id " +
                    "WHERE parking_place.parking_id = ?1 and end_time IS NULL",
            nativeQuery = true)
    List<Event> findActiveParkingEvent(Long parkingPlaceId);

    @Query(
            value = "Select * FROM parking_event WHERE start_time >= ?1 and end_time <= ?2",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDates(Timestamp startTime, Timestamp endTime);
}
