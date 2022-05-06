package com.example.islandbackend.repositories;

import com.example.islandbackend.models.process.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends CrudRepository<Step, String> {
}
