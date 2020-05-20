package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Parking;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
        return "dataEditing/person/addPerson";
    }

    @PostMapping("person/add/{person}")
    public String addPerson(@PathVariable @Valid Person person, BindingResult bindingResult, Model model) {
        boolean success = false;
        if(success) {
            return getPersonsEdit(model);
        }
        else return addPerson(model);
    }

    @GetMapping("person/edit/{person}")
    public String editPerson(@PathVariable Person person, Model model) {
        model.addAttribute("person", person);
        return "dataEditing/person/editPerson";
    }

    @PostMapping("person/edit/{personId}")
    public String editPerson(@PathVariable Long personId, @Valid Person changedPerson, BindingResult bindingResult, Model model) {
        Person person = dataEditingService.getPerson(personId).get();
        return editPerson(person, model);
    }

    @GetMapping("person/delete/{personId}")
    public String deletePerson(@PathVariable Long personId, Model model) {
        return getPersonsEdit(model);
    }
}