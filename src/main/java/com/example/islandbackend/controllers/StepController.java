package com.example.islandbackend.controllers;

import com.example.islandbackend.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StepController {
    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping(
            value = "/steps/{id}/info",
            produces = "application/json"
    )
    @ResponseBody
    String stepInfo(@PathVariable("id") String id) {
        return stepService.info(id);
    }
}
