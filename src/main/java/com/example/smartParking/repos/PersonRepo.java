package com.example.smartParking.repos;


import com.example.smartParking.model.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepo extends CrudRepository<Person, Long> {

    public List<Person> findAll();
}
