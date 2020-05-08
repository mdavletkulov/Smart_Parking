package com.example.smartParking.service;

import com.example.smartParking.domain.*;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.PlaceRepo;
import com.example.smartParking.repos.ParkingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
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
        return placeRepo.findByParkingNum(parkingId);
    }

    public Map<String, ?> getParkingEventAttributes(Model model, Place place) {
        Event event = eventRepo.findActiveParkingEvent(place.getId());
        Automobile automobile = event.getAutomobile();
        Person person = automobile.getPerson();
        boolean statusViolation = place.isSpecialStatus() && !person.isSpecialStatus();
        boolean passViolation = person.getPassNum() == null
                || person.getPassEndDate().getTime() < new Date().getTime();
        return new HashMap<String, Object>() {{
            put("passViolation", passViolation);
            put("violation", statusViolation);
            put("automobile", automobile);
            put("person", person);
            put("parkingEvent", event);
        }};
    }


}
