package com.example.islandbackend.controllers;

import com.example.islandbackend.repositories.StepRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StepController {

    private final StepRepository stepRepository;

    public StepController(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }
}
