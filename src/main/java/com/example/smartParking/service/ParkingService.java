package com.example.smartParking.service;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.model.response.CarNumberResponse;
import com.example.smartParking.repos.AutomobileRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.ParkingRepo;
import com.example.smartParking.repos.PlaceRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier(value = "defaultRestTemplate")
    private RestTemplate restTemplate;

    @Value("${neuralNetwork.address}")
    private String neuralNetworkAddress;

    @Value("${upload.img.path}")
    private String uploadPath;

    public Optional<Event> findEventById(Long eventId) {
        return eventRepo.findById(eventId);
    }

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
        Event event = eventRepo.findActivePlaceEvent(place.getId()).get();
        Map<String, Object> attributes = new HashMap<>();
        if (event.getAutomobile() != null) {
            Automobile automobile = event.getAutomobile();
            attributes.put("automobile", automobile);
            if (event.getPerson() != null) {
                Person person = event.getPerson();
                attributes.put("person", person);
            }
        }
//        attributes.put("violation", event.getStatusViolation());
        attributes.put("personViolation", event.getPersonViolation());
        attributes.put("autoViolation", event.getAutoViolation());
        attributes.put("parkingEvent", event);
        return attributes;
    }

    public List<Event> getActualEvents(Long parkingId) {
        return eventRepo.findActiveParkingEvent(parkingId);
    }

    public Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public void processPollEvent(String resultFileName, Long parkingId) {
        List<String> autoNums = checkAndProcessImage(resultFileName);
        if (autoNums != null && autoNums.isEmpty()) {
            processEmptyParking(parkingId);
        } else if (autoNums != null) {
            processAutoEvents(autoNums, parkingId);
        }
        File file = new File(uploadPath + "/autos/" + resultFileName);
        file.delete();
    }

    public boolean processEvent(MultipartFile image, Model model, String parkingId) throws IOException {
        boolean success;
        String resultFileName = null;
        if (image != null && !image.getOriginalFilename().isEmpty()) {
            success = checkImageMimeType(image, model);
            if (success) {
                resultFileName = loadFile(image);
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Файл не был загружен");
            success = false;
        }
        if (success) {
            List<String> autoNums = checkAndProcessImage(resultFileName);
            if (autoNums != null && autoNums.isEmpty()) {
                processEmptyParking(Long.valueOf(parkingId));
            } else if (autoNums != null) {
                processAutoEvents(autoNums, Long.valueOf(parkingId));
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Фото было обработанно некорректно");
                success = false;
            }
        }
        File file = new File(uploadPath + "/autos/" + resultFileName);
        file.delete();
        return success;
    }

    private boolean checkImageMimeType(MultipartFile image, Model model) {
        String mimetype = image.getContentType();
        String type = mimetype.split("/")[0];
        if (!type.equals("image")) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Загруженный файл не фото");
            return false;
        }
        return true;
    }

    private String loadFile(MultipartFile image) throws IOException {
        String resultFileName;
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        resultFileName = uuidFile + "." + image.getOriginalFilename();

        image.transferTo(new File(uploadPath + "/autos/" + resultFileName));
        return resultFileName;
    }

    private List<String> checkAndProcessImage(String resultFileName) {
        if (resultFileName != null && !resultFileName.isBlank()) {
            return processImage(resultFileName);
        }
        return null;
    }

    private void processAutoEvents(List<String> autoNums, Long parkingId) {
        removePastEvents(autoNums, parkingId);
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        getOnlyNewEvents(autoNums, activeEvents);
        sortAndCreateEvents(autoNums, parkingId);
    }

    private void processEmptyParking(Long parkingId) {
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        for (Event event : activeEvents) {
            Date date = new Date();
            long time = date.getTime();
            event.setEndTime(new Timestamp(time));
            eventRepo.save(event);
        }
    }

    private void removePastEvents(List<String> autoNums, Long parkingId) {
        List<Event> activeWithoutAuto = eventRepo.findActiveParkingEventWithoutAuto(parkingId);
        for (Event eventWithoutAuto : activeWithoutAuto) {
            Date date = new Date();
            long time = date.getTime();
            eventWithoutAuto.setEndTime(new Timestamp(time));
            eventRepo.save(eventWithoutAuto);
        }
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        List<String> activeAutoNums = activeEvents.stream()
                .map(event -> event.getAutomobile().getNumber())
                .collect(Collectors.toList());
        List<String> inactiveAutoNums = (List<String>) CollectionUtils.subtract(activeAutoNums, autoNums);
        for (String number : inactiveAutoNums) {
            Optional<Event> event = eventRepo.findActiveAutoEvent(number);
            if (event.isPresent()) {
                Date date = new Date();
                long time = date.getTime();
                event.get().setEndTime(new Timestamp(time));
                eventRepo.save(event.get());
            }
        }
    }

    private void getOnlyNewEvents(List<String> autoNums, List<Event> activeEvents) {
        for (Event event : activeEvents) {
            autoNums.removeIf(num -> event.getAutomobile().getNumber().equals(num));
        }
    }

    private void sortAndCreateEvents(List<String> autoNums, Long parkingId) {
        for (String autoNum : autoNums) {
            Optional<Automobile> auto = automobileRepo.findByNumber(autoNum);
            if (auto.isPresent()) {
                createEvent(auto.get(), parkingId);
            } else {
                Automobile newAuto = new Automobile();
                newAuto.setNumber(autoNum);
                automobileRepo.save(newAuto);
                createAnonymousEvent(newAuto, parkingId);
            }
        }
    }

    private void createEvent(Automobile automobile, Long parkingId) {
        List<Place> freePlaces = placeRepo.findFreePlacesByParking(parkingId);
        if (!freePlaces.isEmpty()) {
            Event event = new Event();
            event.setAutomobile(automobile);
            event.setPlace(freePlaces.get(0));
            Date date = new Date();
            long time = date.getTime();
            event.setStartTime(new Timestamp(time));
            event.setEndTime(null);
            eventRepo.save(event);
        }
    }

    private void createAnonymousEvent(Automobile automobile, Long parkingId) {
        List<Place> freePlaces = placeRepo.findFreePlacesByParking(parkingId);
        if (!freePlaces.isEmpty()) {
            Event event = new Event();
            event.setAutomobile(automobile);
            event.setPlace(freePlaces.get(0));
            Date date = new Date();
            long time = date.getTime();
            event.setStartTime(new Timestamp(time));
            event.setEndTime(null);
            event.setPassNumViolation(false);
            event.setPersonViolation(true);
            eventRepo.save(event);
        }
    }
//    public boolean processEvent(String parkingId, String placeId, MultipartFile image, Model model, Event event) throws IOException {
//        boolean success = true;
//        String resultFileName = null;
//        String autoNum = null;
//        if (parkingId == null || parkingId.isBlank()) {
//            model.addAttribute("parkingError", "Поле парковки не может быть пустым");
//            success = false;
//        }
//        if (placeId == null || placeId.isBlank()) {
//            model.addAttribute("placeError", "Поле парковочного места не может быть пустым");
//            success = false;
//        }
//        if (image != null && !image.getOriginalFilename().isEmpty()) {
//            String mimetype = image.getContentType();
//            String type = mimetype.split("/")[0];
//            if (!type.equals("image")) {
//                model.addAttribute("messageType", "danger");
//                model.addAttribute("message", "Загруженный файл не фото");
//                success = false;
//            } else {
//                File uploadDir = new File(uploadPath);
//
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdir();
//                }
//
//                String uuidFile = UUID.randomUUID().toString();
//                resultFileName = uuidFile + "." + image.getOriginalFilename();
//
//                image.transferTo(new File(uploadPath + "/autos/" + resultFileName));
//            }
//        } else {
//            model.addAttribute("messageType", "danger");
//            model.addAttribute("message", "Файл не был загружен");
//            success = false;
//        }
//        if (success) {
//            Optional<Place> place1 = findPlace(Integer.valueOf(placeId), Long.valueOf(parkingId));
//            if (place1.isPresent()) {
//                if (eventRepo.findActivePlaceEvent(place1.get().getId()).isPresent()) {
//                    model.addAttribute("messageType", "danger");
//                    model.addAttribute("message", "Это парковочное место занято в данный момент");
//                    success = false;
//                } else {
//                    if (resultFileName != null && !resultFileName.isBlank()) {
//                        autoNum = processImage(resultFileName);
//                    }
//                    if (autoNum != null && !autoNum.isBlank()) {
//                        Optional<Automobile> automobile = getAutoByNum(autoNum);
//                        if (automobile.isPresent()) {
//                            if (eventRepo.findActiveAutoEvent(autoNum).isEmpty()) {
//                                event.setAutomobile(automobile.get());
//                                event.setPlace(place1.get());
//                                event.setEndTime(null);
//                                Date date = new Date();
//                                long time = date.getTime();
//                                event.setStartTime(new Timestamp(time));
//                                event.setPassNumViolation(false);
//                                event.setStatusViolation(false);
//                                eventRepo.save(event);
//                                model.addAttribute("messageType", "success");
//                                model.addAttribute("message", "Парковочное событие было сохранено в базе");
//                            } else {
//                                model.addAttribute("messageType", "danger");
//                                model.addAttribute("message", "Данный автомобиль уже находится на парковочном месте!");
//                                success = false;
//                            }
//                        } else {
//                            model.addAttribute("messageType", "danger");
//                            model.addAttribute("message", "Такого автомобиля в базе не существует!");
//                            success = false;
//                        }
//                    } else {
//                        model.addAttribute("messageType", "danger");
//                        model.addAttribute("message", "Фото было обработанно некорректно");
//                        success = false;
//                    }
//                }
//            } else {
//                model.addAttribute("messageType", "danger");
//                model.addAttribute("message", "Такое парковочное место не найдено");
//                success = false;
//            }
//        }
//        File file = new File(uploadPath + "/autos/" + resultFileName);
//        file.delete();
//        return success;
//    }

    private List<String> processImage(String fileName) {
        ResponseEntity<CarNumberResponse> result = restTemplate.getForEntity(neuralNetworkAddress + "/" + fileName, CarNumberResponse.class);
        return result.getBody().getNumbers();
    }

    public void processStopEvent(String eventId, Model model) {
        if (eventId != null && !eventId.isBlank()) {
            Optional<Event> event = findEventById(Long.valueOf(eventId));
            if (event.isPresent()) {
                Event event1 = event.get();
                if (event1.getEndTime() != null) {
                    model.addAttribute("messageType", "danger");
                    model.addAttribute("message", "Парковочное событие уже было завершено");
                } else {
                    Date date = new Date();
                    long time = date.getTime();
                    event1.setEndTime(new Timestamp(time));
                    eventRepo.save(event1);
                    model.addAttribute("messageType", "success");
                    model.addAttribute("message", "Парковочное событие было завершено");
                }
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Такое парковочное событие не найдено");
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такое парковочное событие не найдено");
        }
    }
}
