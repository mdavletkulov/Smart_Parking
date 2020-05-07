package com.example.smartParking.service;

import com.example.smartParking.domain.*;
import com.example.smartParking.repos.ParkingEventRepo;
import com.example.smartParking.repos.ParkingPlaceRepo;
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
    ParkingPlaceRepo parkingPlaceRepo;

    @Autowired
    ParkingEventRepo parkingEventRepo;

    @Autowired
    ParkingRepo parkingRepo;

    public Iterable<Parking> findAllParking(){
        return parkingRepo.findAll();
    }


    public List<ParkingPlace> findPlaceByParkingId(int parkingId) {
        return parkingPlaceRepo.findByParkingId(parkingId);
    }

    public Map<String, ?> getParkingEventAttributes(Model model, ParkingPlace place) {
        ParkingEvent parkingEvent = parkingEventRepo.findActiveParkingEvent(place.getId());
        Automobile automobile = parkingEvent.getAutomobile();
        Person person = automobile.getPerson();
        boolean statusViolation = place.isSpecialStatus() && !person.isSpecialStatus();
        boolean passViolation = person.getPassNum() == null
                || person.getPassEndDate().getTime() < new Date().getTime();
        return new HashMap<String, Object>() {{
            put("passViolation", passViolation);
            put("violation", statusViolation);
            put("automobile", automobile);
            put("person", person);
            put("parkingEvent", parkingEvent);
        }};
    }


}
