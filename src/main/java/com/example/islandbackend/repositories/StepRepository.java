package com.example.islandbackend.repositories;

import com.example.islandbackend.models.process.Step;
import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
}
