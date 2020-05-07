package com.example.smartParking.controllers;

import com.example.smartParking.domain.Parking;
import com.example.smartParking.domain.ParkingPlace;
import com.example.smartParking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @GetMapping
    public String getAllParking(Model model) {
        model.addAttribute("parkings", parkingService.findAllParking());
        return "parkingsPage";
    }

    @GetMapping("{parking}")
    public String getParking(@PathVariable Parking parking, Model model) {
        model.addAttribute("places", parkingService.findPlaceByParkingId(parking.getId()));
        return "parking";
    }

    @GetMapping("{parking}/{place}")
    public String getPlace(@PathVariable ParkingPlace parkingPlace, Model model) {
        model.addAttribute("placeNum", parkingPlace.getPlaceNumber());
        model.addAttribute("specialStatus", parkingPlace.isSpecialStatus());
        model.addAttribute("parkingName", parkingPlace.getParking().getDescription());
        model.addAllAttributes(parkingService.getParkingEventAttributes(model, parkingPlace));
        return "parkingPlace";
    }


}
