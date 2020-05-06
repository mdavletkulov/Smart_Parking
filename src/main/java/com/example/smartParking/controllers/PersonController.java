package com.example.smartParking.controllers;

import com.example.smartParking.repos.DivisionRepo;
import com.example.smartParking.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonController {

    @Autowired
    public PersonRepo personRepo;

    @Autowired
    public DivisionRepo divisionRepo;

    @GetMapping("/createPerson")
    public void createPerson() {
//        Person person = new Person();
//        person.setFirstName();
//        person.setSecondName();
//        person.setMiddleName();
//        person.setJobPosition();
//        person.setSubdivision();
//        person.setEmployee(true);
//        person.setStudent(false);
//        person.setPassNum(1);
//        person.setPassEndDate(DateUtils.addYears(new Date(), 1));
//        person.setSpecialStatus(false);
    }
}
