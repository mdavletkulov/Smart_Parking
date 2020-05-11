package com.example.smartParking.repos;

import com.example.smartParking.domain.Color;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepo extends CrudRepository<Color, Long> {
}