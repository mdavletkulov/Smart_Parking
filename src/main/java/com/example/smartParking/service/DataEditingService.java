package com.example.smartParking.service;

import com.example.smartParking.Utils.ControllerUtils;
import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Automobile> getAuto(Long id) {
        return automobileRepo.findById(id);
    }

    public List<Division> getAllDivisions() {
        return divisionRepo.findAll();
    }

    public Optional<Division> getDivision(Long id) {
        return divisionRepo.findById(id);
    }

    public Iterable<JobPosition> getAllJobs() {
        return jobPositionRepo.findAll();
    }

    public Optional<JobPosition> getJob(Long id) {
        return jobPositionRepo.findById(id);
    }

    public Iterable<Parking> getAllParking() {
        return parkingRepo.findAll();
    }

    public Optional<Parking> getParking(Long id) {
        return parkingRepo.findById(id);
    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Optional<Person> getPerson(Long id) {
        return personRepo.findById(id);
    }

    public Iterable<Place> getAllPlaces() {
        return placeRepo.findAll();
    }

    public Optional<Place> getPlace(Long id) {
        return placeRepo.findById(id);
    }

    public List<Subdivision> getAllSubdivisions() {
        return subdivisionRepo.findAll();
    }

    public Optional<Subdivision> getSubdivision(Long id) {
        return subdivisionRepo.findById(id);
    }


    //------------------------- Color --------------------------------//

    public Iterable<Color> getAllColors() {
        return colorRepo.findAll();
    }

    public Optional<Color> getColor(Long id) {
        return colorRepo.findById(id);
    }

    public boolean updateColor(Color color, String colorName, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        else {
            color.setName(colorName);
            colorRepo.save(color);
            return true;
        }
    }

    public void deleteColor(Long colorId, Model model) {
        Optional<Color> color = colorRepo.findById(colorId);
        if (color.isPresent()) {
            if(automobileRepo.findAllByColor(color.get().getName()).isEmpty()) {
                colorRepo.deleteById(colorId);
            }
            else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Цвет не может быть удален, так как в базе существую автомобили с этим цветом!");
                return;
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Цвет не найден");
            return;
        }
        color = colorRepo.findById(colorId);
        if (color.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Цвет успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Цвет не был удален");
        }
    }

    public boolean addColor(Color color, BindingResult bindingResult, Model model) {
        Color colorFromDB = colorRepo.findByName(color.getName());

        if (colorFromDB != null) {
            model.addAttribute("colorNameError", "Такой цвет уже существует!");
            return false;
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        colorRepo.save(color);
        model.addAttribute("message", String.format("%s цвет создан!", color.getName()));
        return true;
    }


    //------------------------------------------------------------------//


}
