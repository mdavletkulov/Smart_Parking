package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.JobPosition;
import com.example.smartParking.model.domain.Parking;
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
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
@RequestMapping("/dataEdit")
public class ParkingEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("parking")
    public String getParkingsEdit(Model model) {
        model.addAttribute("parkings", dataEditingService.getAllParking());
        return "dataEditing/parking/parkingList";
    }

    @GetMapping("parking/add")
    public String addParking(Model model) {
        return "dataEditing/parking/addParking";
    }

    @PostMapping("auto/parking/{parking}")
    public String addParking(@PathVariable @Valid Parking parking, BindingResult bindingResult, Model model) {
        if (dataEditingService.addParking(parking, bindingResult, model)) {
            return getParkingsEdit(model);
        } else return addParking(model);
    }

    @GetMapping("parking/edit/{parking}")
    public String editParking(@PathVariable Parking parking, Model model) {
        model.addAttribute("parking", parking);
        return "dataEditing/parking/editParking";
    }

    @PostMapping("parking/edit/{parkingId}")
    public String editParking(@PathVariable Long parkingId, @Valid Parking changedParking, BindingResult bindingResult, Model model) {
        Optional<Parking> parkingDB = dataEditingService.getParking(parkingId);
        boolean success;
        if (parkingDB.isPresent()) {
            success = dataEditingService.updateParking(parkingDB.get(), changedParking, bindingResult, model);
        } else {
            model.addAttribute("message", "Такой парковки не существует");
            return getParkingsEdit(model);
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        parkingDB = dataEditingService.getParking(parkingId);
        return editParking(parkingDB.get(), model);
    }

    @GetMapping("parking/delete/{parkingId}")
    public String deleteParking(@PathVariable Long parkingId, Model model) {
        dataEditingService.deleteParking(parkingId, model);
        return getParkingsEdit(model);
    }
}
