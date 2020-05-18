package com.example.smartParking.service;

import com.example.smartParking.model.ReportEntity;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportCreatorService reportCreatorService;

    @Autowired
    ReportService reportService;

    @Autowired
    EventRepo eventRepo;

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
            if (event.isSpecialStatusViolation()) reportEntity.setStatusViolation("Нарушение правил парковки на место для людей со специальным статусом!");
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

}
