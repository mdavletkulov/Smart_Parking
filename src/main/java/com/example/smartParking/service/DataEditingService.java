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
        } else {
            color.setName(colorName);
            colorRepo.save(color);
            return true;
        }
    }

    public void deleteColor(Long colorId, Model model) {
        Optional<Color> color = colorRepo.findById(colorId);
        if (color.isPresent()) {
            if (automobileRepo.findAllByColor(color.get().getName()).isEmpty()) {
                colorRepo.deleteById(colorId);
            } else {
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

    //---------------------------- Person -----------------------------//

    public void deletePerson(Long personId, Model model) {
        Optional<Person> person = personRepo.findById(personId);
        if (person.isPresent()) {
            if (!automobileRepo.findByPersonId(personId).isEmpty()) {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Водитель не может быть удален, так как в базе существую автомобили этого водителя!");
                return;
            } else personRepo.deleteById(personId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Водитель не найден");
            return;
        }
        person = personRepo.findById(personId);
        if (person.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Водитель успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Водитель не был удален");
        }

    }

    public boolean addPerson(Person person, BindingResult bindingResult, Model model) {
        Optional<Person> personFromDB = personRepo.findById(person.getId());

        if (personFromDB.isPresent()) {
            model.addAttribute("personNameError", "Такой пользователь уже существует");
            return false;
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        personRepo.save(person);
        model.addAttribute("message", String.format("Водитель %s создан!", person.getFullName()));
        return true;
    }

    public boolean updatePerson(Person person, Person personChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            person.setFirstName(personChange.getFirstName());
            person.setSecondName(personChange.getSecondName());
            person.setMiddleName(personChange.getMiddleName());
            person.setCourse(personChange.getCourse());
            person.setEmployee(personChange.isEmployee());
            person.setStudent(personChange.isStudent());
            person.setSubdivision(personChange.getSubdivision());
            person.setJobPosition(personChange.getJobPosition());
            person.setGroupName(personChange.getGroupName());
            person.setPassNum(personChange.getPassNum());
            person.setPassEndDate(personChange.getPassEndDate());
            person.setSpecialStatus(personChange.isSpecialStatus());
            personRepo.save(person);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //--------------------------- Automobile ----------------------------//

    public void deleteAuto(Long autoId, Model model) {
        Optional<Automobile> automobile = automobileRepo.findById(autoId);
        if (automobile.isPresent()) {
             automobileRepo.deleteById(autoId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Автомобиль не найден");
            return;
        }
        automobile = automobileRepo.findById(autoId);
        if (automobile.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Автомобиль успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Автомобиль не был удален");
        }

    }

    public boolean addAuto(Automobile automobile, BindingResult bindingResult, Model model) {
        Optional<Automobile> autoFromDB = automobileRepo.findById(automobile.getId());

        if (autoFromDB.isPresent()) {
            model.addAttribute("automobileNameError", "Такой автомобиль уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        automobileRepo.save(automobile);
        model.addAttribute("message", String.format("Автомобиль %s %s создан!", automobile.getModel(), automobile.getNumber()));
        return true;
    }

    public boolean updateAuto(Automobile automobile, Automobile autoChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            automobile.setModel(autoChange.getModel());
            automobile.setNumber(autoChange.getNumber());
            automobile.setColor(autoChange.getColor());
            automobile.setPerson(autoChange.getPerson());
            automobileRepo.save(automobile);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //----------------------------- Place ------------------------------//

    public void deletePlace(Long placeId, Model model) {
        Optional<Place> place = placeRepo.findById(placeId);
        if (place.isPresent()) {
            placeRepo.deleteById(placeId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковочное место не найдено");
            return;
        }
        place = placeRepo.findById(placeId);
        if (place.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Парковочное место успешно удалено");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковочное место не было удалено");
        }

    }

    public boolean addPlace(Place place, BindingResult bindingResult, Model model) {
        List<Place> placeFromDB = placeRepo.findByParkingAndNum(place.getPlaceNumber(), place.getParking().getId());

        if (!placeFromDB.isEmpty()) {
            model.addAttribute("placeNumberError", "Такое парковочное место уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        placeRepo.save(place);
        model.addAttribute("message", "Парковочное место создано!");
        return true;
    }

    public boolean updatePlace(Place place, Place placeChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            place.setParking(placeChange.getParking());
            place.setPlaceNumber(placeChange.getPlaceNumber());
            place.setSpecialStatus(place.isSpecialStatus());
            placeRepo.save(place);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //---------------------------- Division -----------------------------//

    public void deleteDivision(Long divisionId, Model model) {
        Optional<Division> division = divisionRepo.findById(divisionId);
        if (division.isPresent()) {
            divisionRepo.deleteById(divisionId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Институт не найден");
            return;
        }
        division = divisionRepo.findById(divisionId);
        if (division.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Инстиут успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Инстиут не былы удален");
        }

    }

    public boolean addDivision(Division division, BindingResult bindingResult, Model model) {
        Optional<Division> divisionFromDB = divisionRepo.findByName(division.getName());

        if (divisionFromDB.isPresent()) {
            model.addAttribute("nameError", "Такой институт уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        divisionRepo.save(division);
        model.addAttribute("message", "Институт создан!");
        return true;
    }

    public boolean updateDivision(Division division, Division divisionChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            division.setName(divisionChange.getName());
            divisionRepo.save(division);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //---------------------------- Division -----------------------------//

    public void deleteJob(Long jobId, Model model) {
        Optional<JobPosition> jobPosition = jobPositionRepo.findById(jobId);
        if (jobPosition.isPresent()) {
            jobPositionRepo.deleteById(jobId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Должность не найдена");
            return;
        }
        jobPosition = jobPositionRepo.findById(jobId);
        if (jobPosition.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Должность успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Должность не была удалена");
        }

    }

    public boolean addJob(JobPosition jobPosition, BindingResult bindingResult, Model model) {
        Optional<JobPosition> jobFromDB = jobPositionRepo.findByNamePosition(jobPosition.getNamePosition());

        if (jobFromDB.isPresent()) {
            model.addAttribute("nameError", "Такая должность уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        jobPositionRepo.save(jobPosition);
        model.addAttribute("message", "Должность создана!");
        return true;
    }

    public boolean updateJob(JobPosition jobPosition, JobPosition jobPositionChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            jobPosition.setNamePosition(jobPositionChange.getNamePosition());
            jobPosition.setTypeJobPosition(TypeJobPosition.valueOf(jobPositionChange.getTypeJobPosition()));
            jobPositionRepo.save(jobPosition);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //---------------------------- Parking -----------------------------//

    public void deleteParking(Long parkingId, Model model) {
        Optional<Parking> parking = parkingRepo.findById(parkingId);
        if (parking.isPresent()) {
            parkingRepo.deleteById(parkingId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковка не найдена");
            return;
        }
        parking = parkingRepo.findById(parkingId);
        if (parking.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Парковка успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковка не была удалена");
        }

    }

    public boolean addParking(Parking parking, BindingResult bindingResult, Model model) {
        Optional<Parking> parkingDB = parkingRepo.findByDescription(parking.getDescription());

        if (parkingDB.isPresent()) {
            model.addAttribute("nameError", "Такая парковка уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        parkingRepo.save(parking);
        model.addAttribute("message", "Парковка создана!");
        return true;
    }

    public boolean updateParking(Parking parking, Parking parkingChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            parking.setDescription(parkingChange.getDescription());
            parking.setPlaceNumber(parkingChange.getPlaceNumber());
            parkingRepo.save(parking);
            return true;
        }
    }

    //------------------------------------------------------------------//

    //--------------------------- Subdivision ----------------------------//

    public void deleteSubdivision(Long subdivisionId, Model model) {
        Optional<Subdivision> subdivision = subdivisionRepo.findById(subdivisionId);
        if (subdivision.isPresent()) {
            subdivisionRepo.deleteById(subdivisionId);
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Кафедра не найдена");
            return;
        }
        subdivision = subdivisionRepo.findById(subdivisionId);
        if (subdivision.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Кафедра успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Кафедра не была удалена");
        }

    }

    public boolean addSubdivision(Subdivision subdivision, BindingResult bindingResult, Model model) {
        Optional<Subdivision> subdivisionDB = subdivisionRepo.findByName(subdivision.getName());

        if (subdivisionDB.isPresent()) {
            model.addAttribute("nameError", "Такая кафедра уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        subdivisionRepo.save(subdivision);
        model.addAttribute("message", "Кафедра создана!");
        return true;
    }

    public boolean updateSubdivision(Subdivision subdivision, Subdivision subdivisionChange, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        } else {
            subdivision.setDivision(subdivisionChange.getDivision());
            subdivision.setName(subdivisionChange.getName());
            subdivisionRepo.save(subdivision);
            return true;
        }
    }

}
