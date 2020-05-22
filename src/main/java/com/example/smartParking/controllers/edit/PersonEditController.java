package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Person;
import com.example.smartParking.service.DataEditingService;
import com.example.smartParking.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
@RequestMapping("/dataEdit")
public class PersonEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("person")
    public String getPersonsEdit(Model model) {
        model.addAttribute("persons", dataEditingService.getAllPersons());
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        return "dataEditing/person/personList";
    }

    @GetMapping("person/add")
    public String addPerson(Model model) {
        model.addAttribute("jobPositions", dataEditingService.getAllJobs());
        model.addAttribute("divisions", dataEditingService.getAllDivisions());
        return "dataEditing/person/addPerson";
    }

    @PostMapping("person/add")
    public String addPerson(@Valid Person person, BindingResult bindingResult,
                            @RequestParam String passEndDate,
                            @RequestParam String passNum,
                            @RequestParam String subdivision,
                            Model model) {
        boolean correct = dataEditingService.validatePersonFields(person, passEndDate, passNum, subdivision, model);
        if (dataEditingService.addPerson(person, bindingResult, model, correct)) {
            return getPersonsEdit(model);
        } else return addPerson(model);
    }

    @GetMapping("person/edit/{person}")
    public String editPerson(@PathVariable Person person, Model model) {
        model.addAttribute("person", person);
        return "dataEditing/person/editPerson";
    }

    @PostMapping("person/edit/{personId}")
    public String editPerson(@PathVariable Long personId, @Valid Person changedPerson, BindingResult bindingResult, Model model) {
        Optional<Person> personDB = dataEditingService.getPerson(personId);
        boolean success;
        if (personDB.isPresent()) {
            success = dataEditingService.updatePerson(personDB.get(), changedPerson, bindingResult, model);
        } else {
            model.addAttribute("message", "Такого водителя не существует");
            return getPersonsEdit(model);
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        personDB = dataEditingService.getPerson(personId);
        return editPerson(personDB.get(), model);
    }

    @GetMapping("person/delete/{personId}")
    public String deletePerson(@PathVariable Long personId, Model model) {
        dataEditingService.deletePerson(personId, model);
        return getPersonsEdit(model);
    }
}
