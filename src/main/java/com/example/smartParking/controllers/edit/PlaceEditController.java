package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Parking;
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
public class PlaceEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("place")
    public String getPlacesEdit(Model model) {
        model.addAttribute("places", dataEditingService.getAllPlaces());
        return "dataEditing/place/placeList";
    }

    @GetMapping("place/add")
    public String addPlace(Model model) {
        return "dataEditing/place/addPlace";
    }

    @PostMapping("auto/place/{place}")
    public String addPlace(@PathVariable @Valid Place place, BindingResult bindingResult, Model model) {
        boolean success = false;
        if(success) {
            return getPlacesEdit(model);
        }
        else return addPlace(model);
    }

    @GetMapping("place/edit/{place}")
    public String editPlace(@PathVariable Place place, Model model) {
        model.addAttribute("place", place);
        return "dataEditing/place/editPlace";
    }

    @PostMapping("place/edit/{placeId}")
    public String editPlace(@PathVariable Long placeId, @Valid Place changedPlace, BindingResult bindingResult, Model model) {
        Place place = dataEditingService.getPlace(placeId).get();
        return editPlace(place, model);
    }

    @GetMapping("place/delete/{placeId}")
    public String deletePerson(@PathVariable Long placeId, Model model) {
        return getPlacesEdit(model);
    }
}
