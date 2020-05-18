package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Automobile;
import org.springframework.data.repository.CrudRepository;

public interface AutomobileRepo extends CrudRepository<Automobile, Long> {
}
