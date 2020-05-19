package com.example.smartParking.service;

import com.example.smartParking.model.ReportEntity;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.model.request.ReportRequest;
import com.example.smartParking.repos.DivisionRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.JobPositionRepo;
import com.example.smartParking.repos.SubdivisionRepo;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    ReportCreatorService reportCreatorService;

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

    public Timestamp convertToTimestamp(String dateTime) {
        String pattern = "dd.MM.yyyy HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(dateTime));
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss"));
        return Timestamp.valueOf(localDateTime);
    }

    public List<ReportEntity> createReportEntities(List<Event> events) {
        List<ReportEntity> reportEntities = new ArrayList<>();
        for (Event event : events) {
            Person person = event.getAutomobile().getPerson();
            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setParkingName(event.getPlace().getParking().getDescription());
            reportEntity.setPlaceNum(event.getPlace().getPlaceNumber());
            reportEntity.setAutoNum(event.getAutomobile().getNumber());
            reportEntity.setPersonName(person.getFullName());
            reportEntity.setStartTime(event.getStartTime());
            reportEntity.setEndTime(event.getEndTime());
            reportEntity.setAutoModel(event.getAutomobile().getModel());
            if (person.getCourse() != null) reportEntity.setCourse(person.getCourse().toString());
            if (person.getCourse() != null) reportEntity.setGroup(person.getGroupName());
            if (person.getSubdivision() != null) {
                reportEntity.setDivision(person.getSubdivision().getDivision().getName());
                reportEntity.setSubdivision(person.getSubdivision().getName());
            }
            if (person.getJobPosition() != null) reportEntity.setJobPosition(person.getJobPosition().getNamePosition());
            if (event.isPassViolation()) reportEntity.setPassViolation("Пропуск просрочен или не существует!");
            if (event.isSpecialStatusViolation())
                reportEntity.setStatusViolation("Нарушение правил парковки на место для людей со специальным статусом!");
            reportEntities.add(reportEntity);
        }
        return reportEntities;
    }

    public void createReport(List<Event> events, Model model) {
        if (!events.isEmpty()) {
            List<ReportEntity> reportEntities = reportService.createReportEntities(events);
            String savedPath = reportCreatorService.createDocxReport(reportEntities);
            if (savedPath != null && !savedPath.isBlank()) {
                model.addAttribute("messageType", "success");
                model.addAttribute("message", "Отчет успешно сохранен по пути " + savedPath);
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Произошла ошибка при создании отчета!");
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Нет парковочных событий в этот промежуток времени!");
        }
    }

    public List<Event> findCommonEvents(String typeJob, String division, String subdivision,
                                        String startTime, String endTime,
                                        Boolean student, Boolean employee, Boolean violation) {
        List<Event> events = new ArrayList<>();
        Timestamp startDateTime = reportService.convertToTimestamp(startTime);
        Timestamp endDateTime = reportService.convertToTimestamp(endTime);
        if (student) {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivisionStudent(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivisionStudent(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDatesStudent(startDateTime, endDateTime);
                if (violation) events = findOnlyViolation(events);
            }
        } else if (employee) {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivisionEmployee(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivisionEmployee(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else if (typeJob != null && !typeJob.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByJobType(startDateTime, endDateTime, typeJob);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDatesEmployee(startDateTime, endDateTime);
                if (violation) events = findOnlyViolation(events);
            }
        } else {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivision(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivision(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDates(startDateTime, endDateTime);
                if (violation) events = findOnlyViolation(events);
            }
        }
        return events;
    }

    private List<Event> findOnlyViolation(List<Event> events) {
        return events.stream().filter(event -> event.isPassViolation() || event.isSpecialStatusViolation())
                .collect(Collectors.toList());
    }

    public void addDefaultAttributes(Model model) {
        model.addAttribute("typesJob", jobPositionRepo.findAllPositionNames());
        model.addAttribute("divisions", divisionRepo.findAll());
    }

    public boolean checkDates(String startTime, String endTime, Model model) {
        boolean correct = true;
        if (startTime == null || startTime.isBlank()) {
            model.addAttribute("startTimeError", "Введите начальную дату!");
            correct = false;
        }
        if (endTime == null || endTime.isBlank()) {
            model.addAttribute("endTimeError", "Введите конечную дату!");
            correct = false;
        }
        return correct;
    }

    public Boolean checkStudent(String student, String employee) {
        boolean studentPresent = false;
        if (student != null && !student.isBlank() && (employee == null || employee.isBlank())) {
            studentPresent = true;
        }
        return studentPresent;
    }

    public Boolean checkEmployee(String student, String employee) {
        boolean employeePresent = false;
        if (employee != null && !employee.isBlank() && (student == null || student.isBlank())) {
            employeePresent = true;
        }
        return employeePresent;
    }

    public Boolean checkViolation(String violation) {
        boolean violationPresent = false;
        if (violation != null && !violation.isBlank()) {
            violationPresent = true;
        }
        return violationPresent;
    }

}
