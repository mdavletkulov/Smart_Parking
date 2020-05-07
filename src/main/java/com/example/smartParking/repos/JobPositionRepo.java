package com.example.smartParking.repos;

import com.example.smartParking.domain.JobPosition;
import org.springframework.data.repository.CrudRepository;

public interface JobPositionRepo extends CrudRepository<JobPosition, Long> {
}
