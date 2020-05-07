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

    }
}
