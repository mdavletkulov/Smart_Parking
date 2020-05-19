package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.repos.*;
import com.example.smartParking.service.ReportService;
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
    DivisionRepo divisionRepo;

    @Autowired
    SubdivisionRepo subdivisionRepo;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    AutomobileRepo automobileRepo;

    @GetMapping
    public String getReportsPage() {
        return "report/reportMain";
    }

    @GetMapping("common")
    public String getCommonReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_common", "yes");
        return "report/commonReport";
    }

    @GetMapping("common/student")
    public String getCommonStudentReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_student", "yes");
        model.addAttribute("student", "yes");
        return "report/commonReport";
    }

    @GetMapping("common/employee")
    public String getCommonEmployeeReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("criteria", "yes");
        model.addAttribute("criteria_employee", "yes");
        model.addAttribute("employee", "yes");
        return "report/commonReport";
    }

    @GetMapping("common/violation")
    public String getCommonViolationReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_common", "yes");
        return "report/commonReport";
    }

    @GetMapping("common/student/violation")
    public String getCommonViolationStudentReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_student", "yes");
        model.addAttribute("student", "yes");
        return "report/commonReport";
    }

    @GetMapping("common/employee/violation")
    public String getCommonViolationEmployeeReports(Model model) {
        reportService.addDefaultAttributes(model);
        model.addAttribute("violation", "yes");
        model.addAttribute("violation_employee", "yes");
        model.addAttribute("employee", "yes");
        return "report/commonReport";
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
        boolean onlyViolation = reportService.checkViolation(violation);
        reportService.addDefaultAttributes(model);
        if (!reportService.checkDates(startTime, endTime, model)) {
            if (onlyViolation) return getCommonViolationReports(model);
            else return "report/commonReport";
        } else {
            boolean studentPresent = reportService.checkStudent(student, employee);
            boolean employeePresent = reportService.checkEmployee(student, employee);
            List<Event> events = reportService.findCommonEvents(typesJob, division, subdivision, startTime,
                    endTime, studentPresent, employeePresent, onlyViolation);
            reportService.createReport(events, model);
        }
        if (onlyViolation) return getCommonViolationReports(model);
        else return "report/commonReport";
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
        return "report/reportSimple";
    }

    @PostMapping("simple")
    public String createSimpleReport(@RequestParam String startTime,
                                     @RequestParam String endTime,
                                     @RequestParam(required = false) String personId,
                                     Model model) {
        boolean personIdPresent = personId != null && !personId.isBlank();
        if (!reportService.checkDates(startTime, endTime, model)) {
            if (personIdPresent)
                return createPersonReport(Long.valueOf(personId), model);
            else return "report/reportSimple";
        }
        List<Event> events = reportService.findSimpleEvents(startTime, endTime, personId);
        reportService.createReport(events, model);
        if (personIdPresent) return createPersonReport(Long.valueOf(personId), model);
        else return "report/reportSimple";
    }

    @GetMapping("person")
    public String showPersonReport(Model model) {
        model.addAttribute("person", "yes");
        model.addAttribute("persons", personRepo.findAll());
        model.addAttribute("automobiles", automobileRepo.findAll());
        return "report/personReport";
    }

    @GetMapping("person/{personId}")
    public String createPersonReport(@PathVariable Long personId, Model model) {
        model.addAttribute("personId", personId);
        return "report/reportSimple";
    }


}
