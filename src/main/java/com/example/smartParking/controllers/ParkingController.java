package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.model.domain.dto.UpdateParking;
import com.example.smartParking.service.DataEditingService;
import com.example.smartParking.service.ParkingService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("events")
    public String getAllEvents(Model model) {
        model.addAttribute("events", parkingService.getAllEvents());
        return "parking/eventList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("adminEvents")
    public String getAllEventsAdmin(Model model) {
        model.addAttribute("events", parkingService.getAllEvents());
        return "parking/eventListAdmin";
    }

    @GetMapping
    public String getAllParking(Model model) {
        ArrayList<Parking> parkings = Lists.newArrayList(parkingService.findAllParking());
        Map<Long, Integer> placeNumbers = new HashMap<>();
        for (Parking parking : parkings) {
            placeNumbers.put(parking.getId(), dataEditingService.getPlaceNumbersOfParking(parking.getId()));
        }
        model.addAttribute("placeNumbers", placeNumbers);
        model.addAttribute("parkings", parkings);
        model.addAttribute("parkingsSize", parkings.size());
        return "parking/parkingsPage";
    }

    @GetMapping("{parking}")
    public String getParking(@PathVariable Parking parking, Model model) {
        ArrayList<Place> places = Lists.newArrayList(parkingService.findPlacesByParkingId(parking.getId()));
        model.addAttribute("parking", parking);
        model.addAttribute("places", places);
        model.addAttribute("placesSize", places.size());
        return "parking/placesPage";
    }

    @GetMapping("{parking}/{place}")
    public String getPlace(@PathVariable Place place, Model model) {
        model.addAttribute("placeNum", place.getPlaceNumber());
        model.addAttribute("specialStatus", place.isSpecialStatus());
        model.addAttribute("parkingName", place.getParking().getDescription());
        model.addAllAttributes(parkingService.getParkingEventAttributes(model, place));
        return "parking/placeProfile";
    }

    @GetMapping(value = "updateStatus/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<UpdateParking> updateStatus(@PathVariable Long parkingId) {
        List<Event> actualEvents = parkingService.getActualEvents(parkingId);
        List<Place> parkingPlaces = parkingService.findPlacesByParkingId(parkingId);
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
                    updateParking.setViolation(event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation());
                    updateParking.setActive(true);
                }
            }).collect(Collectors.toList());

        }
        return updateParkings;
    }

    @GetMapping(value = "getPlaces/{parking}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getSubdivisions(@PathVariable String parking) {
        List<String> places = new ArrayList<>();
        Long parkingId = Long.valueOf(parking);
        for (Place place : parkingService.findPlacesByParkingId(parkingId)) {
            places.add(place.getPlaceNumber().toString());
        }
        return places;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("addEvent")
    public String addEventPage(Model model) {
        model.addAttribute("parkings", parkingService.findAllParking());
        return "admin/addParkingPhoto";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("addEvent")
    public String addEvent(@AuthenticationPrincipal User currentUser,
                           @RequestParam(required = false) String parking,
                           @RequestParam(required = false) String place,
                           Model model,
                           @RequestParam("image") MultipartFile image) throws IOException {
        boolean success = parkingService.processEvent(image, model, parking);
        if (success) return getAllEventsAdmin(model);
        else return addEventPage(model);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("event/stop/{eventId}")
    public String stopEvent(@PathVariable String eventId, Model model) {
        parkingService.processStopEvent(eventId, model);
        model.addAttribute("events", parkingService.getAllEvents());
        return "parking/eventListAdmin";
    }

}
