package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Division;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DivisionRepo extends CrudRepository<Division, Long> {

    List<Division> findAll();
}
