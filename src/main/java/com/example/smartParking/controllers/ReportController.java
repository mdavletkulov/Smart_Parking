package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.repos.DivisionRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.JobPositionRepo;
import com.example.smartParking.repos.SubdivisionRepo;
import com.example.smartParking.service.ReportService;
import javafx.util.Pair;
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

    @Autowired
    JobPositionRepo jobPositionRepo;

    @GetMapping
    public String getReportsPage() {
        return "reportMain";
    }

    @GetMapping("common")
    public String getCommonReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_common", "yes");
        return "commonReport";
    }

    @GetMapping("common/student")
    public String getCommonStudentReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_student", "yes");
        model.addAttribute("student", "yes");
        return "commonReport";
    }

    @GetMapping("common/employee")
    public String getCommonEmployeeReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_employee", "yes");
        model.addAttribute("employee", "yes");
        return "commonReport";
    }

    @GetMapping("common/violation")
    public String getCommonViolationReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_common", "yes");
        return "commonReport";
    }

    @GetMapping("common/student/violation")
    public String getCommonViolationStudentReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_student", "yes");
        model.addAttribute("student", "yes");
        return "commonReport";
    }

    @GetMapping("common/employee/violation")
    public String getCommonViolationEmployeeReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_employee", "yes");
        model.addAttribute("employee", "yes");
        return "commonReport";
    }

    @PostMapping(value = "common")
    public String createCommonReport(Model model,
                                     @RequestParam(required = false) String typesJob,
                                     @RequestParam(required = false) String division,
                                     @RequestParam(required = false) String subdivision,
                                     @RequestParam String startTime,
                                     @RequestParam String endTime,
                                     @RequestParam(required = false) String student,
                                     @RequestParam(required = false) String employee,
                                     @RequestParam(required = false) String violation) {
        reportService.addDefaultAttributes(model);
        if (!reportService.checkDates(startTime, endTime, model)) return "commonReport";
        else {
            boolean studentPresent = reportService.checkStudent(student, employee);
            boolean employeePresent = reportService.checkEmployee(student, employee);
            boolean onlyViolation = reportService.checkViolation(violation);
            List<Event> events = reportService.findCommonEvents(typesJob, division, subdivision, startTime,
                    endTime, studentPresent, employeePresent, onlyViolation);
            reportService.createReport(events, model);
        }
        return "commonReport";
    }

    @GetMapping(value = "common/subdivision/{division}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getSubdivisions(@PathVariable String division) {
        List<String> subdivisions = new ArrayList<>();
        for (Subdivision subdivision : subdivisionRepo.findByDivision(division)) {
            subdivisions.add(subdivision.getName());
        }
        return subdivisions;
    }

    @GetMapping(value = "common/division/{typeJob}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getDivisions(@PathVariable String typeJob) {
        List<String> divisions = new ArrayList<>();
        for (Division division : divisionRepo.findByTypeJob(typeJob)) {
            divisions.add(division.getName());
        }
        return divisions;
    }


    @GetMapping("simple")
    public String showSimpleReport(Model model) {
        model.addAttribute("simple", "yes");
        return "reportSimple";
    }

    @PostMapping("simple")
    public String createReport(@RequestParam String startTime,
                               @RequestParam String endTime,
                               Model model) {
        Timestamp startDateTime = reportService.convertToTimestamp(startTime);
        Timestamp endDateTime = reportService.convertToTimestamp(endTime);
        List<Event> events = eventRepo.findAllParkingBetweenDates(startDateTime, endDateTime);
        reportService.createReport(events, model);
        return "reportSimple";
    }


}
