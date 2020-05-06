package com.example.smartParking.repos;

import com.example.smartParking.domain.Subdivision;
import org.springframework.data.repository.CrudRepository;

public interface SubdivisionRepo extends CrudRepository<Subdivision, Long> {
}
