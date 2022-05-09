package com.example.islandbackend.controllers;

import com.example.islandbackend.services.IslandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IslandController {
    private final IslandService islandService;

    @Autowired
    public IslandController(IslandService islandService) {
        this.islandService = islandService;
    }

    @PostMapping("/islands/{id}/info")
    @ResponseBody
    String islandInfo(@PathVariable("id") String id) {
        return islandService.info(id);
    }

    @PostMapping("/islands/{id}/summary")
    @ResponseBody
    String islandSummary(@PathVariable("id") String id) {
        return islandService.summary(id);
    }
}

