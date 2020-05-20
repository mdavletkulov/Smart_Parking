package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Automobile;
import com.example.smartParking.model.domain.Color;
import com.example.smartParking.model.domain.Division;
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
public class AutoEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("auto")
    public String getAutosEdit(Model model) {
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        return "dataEditing/auto/autoList";
    }

    @GetMapping("auto/add")
    public String addAuto(Model model) {
        return "dataEditing/auto/addAuto";
    }

    @PostMapping("auto/add/{auto}")
    public String addAuto(@PathVariable @Valid Automobile automobile, BindingResult bindingResult, Model model) {
        boolean success = false;
        if(success) {
            return getAutosEdit(model);
        }
        else return addAuto(model);
    }

    @GetMapping("auto/edit/{auto}")
    public String editAuto(@PathVariable Automobile auto, Model model) {
        model.addAttribute("auto", auto);
        return "dataEditing/auto/editAuto";
    }

    @PostMapping("auto/edit/{autoId}")
    public String editAuto(@PathVariable Long autoId, @Valid Automobile changedAuto, BindingResult bindingResult, Model model) {
        Automobile auto = dataEditingService.getAuto(autoId).get();
        return editAuto(auto, model);
    }

    @GetMapping("auto/delete/{autoId}")
    public String deleteAuto(@PathVariable Long autoId, Model model) {
        return getAutosEdit(model);
    }
}
