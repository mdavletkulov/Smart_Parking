package com.example.smartParking.repos;

import com.example.smartParking.model.domain.JobPosition;
import org.springframework.data.repository.CrudRepository;

public interface JobPositionRepo extends CrudRepository<JobPosition, Long> {
}
