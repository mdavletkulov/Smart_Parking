package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Automobile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutomobileRepo extends CrudRepository<Automobile, Long> {

    List<Automobile> findAll();
}
