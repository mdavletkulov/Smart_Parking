package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Person;
import com.example.smartParking.model.domain.Place;
import com.example.smartParking.model.domain.Subdivision;
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
public class SubdivisionEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("subdivision")
    public String getSubdivisionsEdit(Model model) {
        model.addAttribute("subdivisions", dataEditingService.getAllSubdivisions());
        return "dataEditing/subdivision/subdivisionList";
    }

    @GetMapping("subdivision/add")
    public String addSubdivision(Model model) {
        return "dataEditing/subdivision/addSubdivision";
    }

    @PostMapping("auto/subdivision/{subdivision}")
    public String addSubdivision(@PathVariable @Valid Subdivision subdivision, BindingResult bindingResult, Model model) {
        boolean success = false;
        if(success) {
            return getSubdivisionsEdit(model);
        }
        else return addSubdivision(model);
    }

    @GetMapping("subdivision/edit/{subdivision}")
    public String editSubdivision(@PathVariable Subdivision subdivision, Model model) {
        model.addAttribute("subdivision", subdivision);
        return "dataEditing/subdivision/editSubdivision";
    }

    @PostMapping("subdivision/edit/{subdivisionId}")
    public String editSubdivision(@PathVariable Long subdivisionId, @Valid Subdivision changedSubdivision, BindingResult bindingResult, Model model) {
        Subdivision subdivision = dataEditingService.getSubdivision(subdivisionId).get();
        return editSubdivision(subdivision, model);
    }

    @GetMapping("subdivision/delete/{subdivisionId}")
    public String deletePerson(@PathVariable Long subdivisionId, Model model) {
        return getSubdivisionsEdit(model);
    }
}
