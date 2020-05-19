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

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id LEFT JOIN person p on a.person_id = p.id" +
                    " WHERE start_time >= ?1 and end_time <= ?2 and p.student=1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesStudent(Timestamp startTime, Timestamp endTime);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id LEFT JOIN person p on a.person_id = p.id" +
                    " WHERE start_time >= ?1 and end_time <= ?2 and p.employee=1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesEmployee(Timestamp startTime, Timestamp endTime);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id" +
                    " LEFT JOIN person p on a.person_id = p.id LEFT JOIN job_position jp on p.job_position = jp.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and type_job_position = ?3",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesByJobType(Timestamp startTime, Timestamp endTime, String typeJobPosition);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "LEFT JOIN division d on s.division_id = d.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and d.name = ?3",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesByDivision(Timestamp startTime, Timestamp endTime, String divisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and s.name = ?3",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesBySubdivision(Timestamp startTime, Timestamp endTime, String subdivisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "LEFT JOIN division d on s.division_id = d.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and d.name = ?3 and p.student = 1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesByDivisionStudent(Timestamp startTime, Timestamp endTime, String divisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and s.name = ?3 and p.student = 1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesBySubdivisionStudent(Timestamp startTime, Timestamp endTime, String subdivisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "LEFT JOIN division d on s.division_id = d.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and d.name = ?3 and p.employee = 1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesByDivisionEmployee(Timestamp startTime, Timestamp endTime, String divisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id LEFT JOIN subdivision s on p.subdivision = s.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and s.name = ?3 and p.employee = 1",
            nativeQuery = true)
    List<Event> findAllParkingBetweenDatesBySubdivisionEmployee(Timestamp startTime, Timestamp endTime, String subdivisionName);

    @Query(
            value = "Select * FROM parking_event LEFT JOIN automobile a on parking_event.automobile_id = a.id " +
                    "LEFT JOIN person p on a.person_id = p.id " +
                    "WHERE start_time >= ?1 and end_time <= ?2 and p.id = ?3",
            nativeQuery = true)
    List<Event> findAllPersonParkingBetweenDates(Timestamp startDateTime, Timestamp endDateTime, Long personIdL);
}
