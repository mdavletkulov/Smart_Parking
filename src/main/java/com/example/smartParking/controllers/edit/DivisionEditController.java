package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Color;
import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.JobPosition;
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
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
@RequestMapping("/dataEdit")
public class DivisionEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("division")
    public String getDivisionsEdit(Model model) {
        model.addAttribute("divisions", dataEditingService.getAllDivisions());
        return "dataEditing/division/divisionList";
    }

    @GetMapping("division/add")
    public String addDivision(Model model) {
        return "dataEditing/division/addDivision";
    }

    @PostMapping("division/add/{division}")
    public String addDivision(@PathVariable @Valid Division division, BindingResult bindingResult, Model model) {
        if (dataEditingService.addDivision(division, bindingResult, model)) {
            return getDivisionsEdit(model);
        } else return addDivision(model);
    }

    @GetMapping("division/edit/{division}")
    public String editDivision(@PathVariable Division division, Model model) {
        model.addAttribute("division", division);
        return "dataEditing/division/editDivision";
    }

    @PostMapping("division/edit/{divisionId}")
    public String editDivision(@PathVariable Long divisionId, @Valid Division changedDivision, BindingResult bindingResult, Model model) {
        Optional<Division> divisionDB = dataEditingService.getDivision(divisionId);
        boolean success;
        if (divisionDB.isPresent()) {
            success = dataEditingService.updateDivision(divisionDB.get(), changedDivision, bindingResult, model);
        } else {
            model.addAttribute("message", "Такого института не существует");
            return getDivisionsEdit(model);
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        divisionDB = dataEditingService.getDivision(divisionId);
        return editDivision(divisionDB.get(), model);
    }

    @GetMapping("division/delete/{divisionId}")
    public String deleteDivision(@PathVariable Long divisionId, Model model) {
        dataEditingService.deleteDivision(divisionId, model);
        return getDivisionsEdit(model);
    }
}
