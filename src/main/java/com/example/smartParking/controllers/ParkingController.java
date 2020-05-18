package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Parking;
import com.example.smartParking.model.domain.Place;
import com.example.smartParking.model.domain.dto.UpdateParking;
import com.example.smartParking.service.ParkingService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @GetMapping
    public String getAllParking(Model model) {
        ArrayList<Parking> parkings = Lists.newArrayList(parkingService.findAllParking());
        model.addAttribute("parkings", parkings);
        model.addAttribute("parkingsSize", parkings.size());
        return "parkingsPage";
    }

    @GetMapping("{parking}")
    public String getParking(@PathVariable Parking parking, Model model) {
        ArrayList<Place> places = Lists.newArrayList(parkingService.findPlaceByParkingId(parking.getId()));
        model.addAttribute("parking", parking);
        model.addAttribute("places", places);
        model.addAttribute("placesSize", places.size());
        return "placesPage";
    }

    @GetMapping("{parking}/{place}")
    public String getPlace(@PathVariable Place place, Model model) {
        model.addAttribute("placeNum", place.getPlaceNumber());
        model.addAttribute("specialStatus", place.isSpecialStatus());
        model.addAttribute("parkingName", place.getParking().getDescription());
        model.addAllAttributes(parkingService.getParkingEventAttributes(model, place));
        return "placeProfile";
    }

    @GetMapping(value = "updateStatus/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UpdateParking> updateStatus(@PathVariable Long parkingId) {
        List<Event> actualEvents = parkingService.getActualEvents(parkingId);
        List<Place> parkingPlaces = parkingService.findPlaceByParkingId(parkingId);
        List<UpdateParking> updateParkings = new ArrayList<>();
        for (Place place : parkingPlaces) {
            UpdateParking updateParking = new UpdateParking();
            updateParking.setPlaceId(place.getId());
            updateParking.setPlaceNum(place.getPlaceNumber());
            updateParkings.add(updateParking);
        }
        for (Event event : actualEvents) {
            updateParkings.stream().peek(updateParking -> {
               if (updateParking.getPlaceId().equals(event.getPlace().getId())) {
                   updateParking.setViolation(event.isPassViolation() || event.isSpecialStatusViolation());
                   updateParking.setActive(true);
               }
            }).collect(Collectors.toList());

        }
        return updateParkings;
    }

}
