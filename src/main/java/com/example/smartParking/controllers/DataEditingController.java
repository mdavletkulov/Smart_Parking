package com.example.smartParking.controllers;

import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
@RequestMapping("/dataEdit")
public class DataEditingController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping
    public String getMainPage() {
        return "dataEditing/chooseData";
    }

    @GetMapping("person")
    public String getPersonsEdit(Model model) {
        model.addAttribute("persons", dataEditingService.getAllPersons());
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        return "dataEditing/person/personList";
    }

    @GetMapping("auto")
    public String getAutosEdit(Model model) {
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        return "dataEditing/auto/autoList";
    }

    @GetMapping("color")
    public String getColorsEdit(Model model) {
        model.addAttribute("colors", dataEditingService.getAllColors());
        return "dataEditing/color/colorList";
    }

    @GetMapping("division")
    public String getDivisionsEdit(Model model) {
        model.addAttribute("divisions", dataEditingService.getAllDivisions());
        return "dataEditing/division/divisionList";
    }

    @GetMapping("parking")
    public String getParkingsEdit(Model model) {
        model.addAttribute("parkings", dataEditingService.getAllParking());
        return "dataEditing/parking/parkingList";
    }

    @GetMapping("place")
    public String getPlacesEdit(Model model) {
        model.addAttribute("places", dataEditingService.getAllPlaces());
        return "dataEditing/place/placeList";
    }

    @GetMapping("subdivision")
    public String getSubdivisionsEdit(Model model) {
        model.addAttribute("subdivisions", dataEditingService.getAllSubdivisions());
        return "dataEditing/subdivision/subdivisionList";
    }

    @GetMapping("job")
    public String getJobEdit(Model model) {
        model.addAttribute("jobs", dataEditingService.getAllJobs());
        return "dataEditing/job/jobList";
    }
}
