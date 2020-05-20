package com.example.smartParking.service;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataEditingService {

    @Autowired
    AutomobileRepo automobileRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    DivisionRepo divisionRepo;

    @Autowired
    JobPositionRepo jobPositionRepo;

    @Autowired
    ParkingRepo parkingRepo;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    SubdivisionRepo subdivisionRepo;

    public List<Automobile> getAllAutos() {
        return automobileRepo.findAll();
    }

    public Iterable<Color> getAllColors() {
        return colorRepo.findAll();
    }

    public List<Division> getAllDivisions() {
        return divisionRepo.findAll();
    }

    public Iterable<JobPosition> getAllJobs() {
        return jobPositionRepo.findAll();
    }

    public Iterable<Parking> getAllParking() {
        return parkingRepo.findAll();
    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Iterable<Place> getAllPlaces() {
        return placeRepo.findAll();
    }

    public List<Subdivision> getAllSubdivisions() {
        return subdivisionRepo.findAll();
    }



}
