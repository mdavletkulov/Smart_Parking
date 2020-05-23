package com.example.smartParking.service;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.ParkingRepo;
import com.example.smartParking.repos.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingService {

    @Autowired
    EventRepo eventRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    ParkingRepo parkingRepo;

    public Iterable<Parking> findAllParking(){
        return parkingRepo.findAll();
    }


    public List<Place> findPlaceByParkingId(Long parkingId) {
        return placeRepo.findByParkingId(parkingId);
    }

    public Map<String, ?> getParkingEventAttributes(Model model, Place place) {
        Event event = eventRepo.findActivePlaceEvent(place.getId());
        Map<String, Object> attributes = new HashMap<>();
        if (event.getAutomobile() != null) {
            Automobile automobile = event.getAutomobile();
            attributes.put("automobile", automobile);
            if (event.getPerson() != null) {
                Person person = event.getPerson();
                attributes.put("person", person);
            }
        }
        attributes.put("violation", event.getStatusViolation());
        attributes.put("parkingEvent", event);
        return attributes;
    }

    public List<Event> getActualEvents(Long parkingId) {
        return eventRepo.findActiveParkingEvent(parkingId);
    }

    public Iterable<Event> getAllEvents() {return eventRepo.findAll();}


}
