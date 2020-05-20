package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Automobile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AutomobileRepo extends CrudRepository<Automobile, Long> {

    List<Automobile> findAll();

    @Query(
            value = "SELECT * FROM automobile LEFT JOIN color c on automobile.color_id = c.id  where c.name = ?1",
            nativeQuery = true)
    List<Automobile> findAllByColor(String colorName);
}
