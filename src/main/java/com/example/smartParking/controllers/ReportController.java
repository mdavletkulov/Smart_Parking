package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.repos.DivisionRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.SubdivisionRepo;
import com.example.smartParking.service.ReportService;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    DivisionRepo divisionRepo;

    @Autowired
    SubdivisionRepo subdivisionRepo;

    @GetMapping("subdivision")
    public String getReports(Model model) {
        model.addAttribute("divisions", divisionRepo.findAll());
        return "subdivisionReport";
    }

    @GetMapping(value = "subdivision/get/{division}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getSubdivisions(@PathVariable String division) {
        List<String> subdivisions = new ArrayList<>();
        for (Subdivision subdivision : subdivisionRepo.findByDivision(division)) {
            subdivisions.add(subdivision.getName());
        }
        return subdivisions;
    }


    @PostMapping("subdivision")
    public String createReport(@RequestParam String startTime,
                               @RequestParam String endTime,
                               @RequestParam String division,
                               Model model) {
        Timestamp startDateTime = reportService.convertToTimestamp(startTime);
        Timestamp endDateTime = reportService.convertToTimestamp(endTime);
        List<Event> events = eventRepo.findAllParkingBetweenDates(startDateTime, endDateTime);
        reportService.createReport(events, model);
        return "subdivisionReport";
    }


}
