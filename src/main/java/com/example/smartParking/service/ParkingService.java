package com.example.smartParking.service;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.AutomobileRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.ParkingRepo;
import com.example.smartParking.repos.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ParkingService {

    @Autowired
    EventRepo eventRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    ParkingRepo parkingRepo;

    @Autowired
    AutomobileRepo automobileRepo;

    public Iterable<Parking> findAllParking() {
        return parkingRepo.findAll();
    }

    public List<Place> findByNumAndParking(Integer placeNum, Long parkingId) {
        return placeRepo.findByParkingAndNum(placeNum, parkingId);
    }

    public Optional<Place> findPlace(Integer placeNum, Long parkingId) {
        return placeRepo.findPlace(placeNum, parkingId);
    }

    public Optional<Automobile> getAutoByNum(String number) {
        return automobileRepo.findByNumber(number);
    }


    public List<Place> findPlacesByParkingId(Long parkingId) {
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

    public Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public boolean processEvent(String parkingId, String placeId, MultipartFile image, Model model, Event event) {
        boolean success = true;
        if (parkingId == null || parkingId.isBlank()) {
            model.addAttribute("parkingError", "Поле парковки не может быть пустым");
            success = false;
        }
        if (placeId == null || placeId.isBlank()) {
            model.addAttribute("placeError", "Поле парковочного места не может быть пустым");
            success = false;
        }
        if (image != null && !image.getOriginalFilename().isEmpty()) {
            String mimetype = image.getContentType();
            String type = mimetype.split("/")[0];
            if (!type.equals("image")) {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Загруженный файл не фото");
                success = false;
            }
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Файл не был загружен");
            success = false;
        }
        if (success) {
            Optional<Place> place1 = findPlace(Integer.valueOf(placeId), Long.valueOf(parkingId));
            if (place1.isPresent()) {
                String autoNum = proccessImage(image);
                if (autoNum != null && !autoNum.isBlank()) {
                    Optional<Automobile> automobile = getAutoByNum(autoNum);
                    if (automobile.isPresent()) {
                        event.setAutomobile(automobile.get());
                        event.setPlace(place1.get());
                        event.setEndTime(null);
                        Date date = new Date();
                        long time = date.getTime();
                        event.setStartTime(new Timestamp(time));
                        eventRepo.save(event);
                        model.addAttribute("messageType", "success");
                        model.addAttribute("message", "Парковочное событие было сохранено в базе");
                    }
                    else {
                        model.addAttribute("messageType", "danger");
                        model.addAttribute("message", "Такого автомобиля в базе не существует!");
                        success = false;
                    }
                }
                else {
                    model.addAttribute("messageType", "danger");
                    model.addAttribute("message", "Фото было обработанно некорректно");
                    success = false;
                }
            }
            else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Такое парковочное место не найдено");
                success = false;
            }
        }
        return success;
    }
}
