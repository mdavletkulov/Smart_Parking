package com.example.smartParking.repos;


import com.example.smartParking.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepo extends CrudRepository<Person, Long> {
}
