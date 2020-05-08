package com.example.smartParking.controllers;

import com.example.smartParking.domain.Parking;
import com.example.smartParking.domain.Place;
import com.example.smartParking.service.ParkingService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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


}
