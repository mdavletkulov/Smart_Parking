package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Color;
import com.example.smartParking.model.domain.Division;
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
public class ColorEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("color")
    public String getColorsEdit(Model model) {
        model.addAttribute("colors", dataEditingService.getAllColors());
        return "dataEditing/color/colorList";
    }

    @GetMapping("color/add")
    public String addColor(Model model) {
        return "dataEditing/color/addColor";
    }

    @PostMapping("auto/color/{color}")
    public String addColor(@PathVariable @Valid Color color, BindingResult bindingResult, Model model) {
        boolean success = false;
        if (dataEditingService.addColor(color, bindingResult, model)) {
            return getColorsEdit(model);
        } else return addColor(model);
    }

    @GetMapping("color/edit/{color}")
    public String editColor(@PathVariable Color color, Model model) {
        model.addAttribute("color", color);
        return "dataEditing/color/editColor";
    }

    @PostMapping("color/edit/{colorId}")
    public String editColor(@PathVariable Long colorId, @Valid Division changedColor, BindingResult bindingResult, Model model) {
        Optional<Color> color = dataEditingService.getColor(colorId);
        boolean success = false;
        if (color.isPresent()) {
            success = dataEditingService.updateColor(color.get(), changedColor.getName(), bindingResult, model);
        } else {
            model.addAttribute("message", "Такого цвета не существует");
            return getColorsEdit(model);
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return editColor(color.get(), model);
    }

    @GetMapping("color/delete/{colorId}")
    public String deleteColor(@PathVariable Long colorId, Model model) {
        dataEditingService.deleteColor(colorId, model);
        return getColorsEdit(model);
    }
}