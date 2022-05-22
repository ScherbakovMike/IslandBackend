package com.example.islandbackend.services;

import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.SessionDispatcher;
import com.example.islandbackend.presentators.FieldsSummaryPresentator;
import com.example.islandbackend.presentators.IslandPresentator;
import com.example.islandbackend.presentators.JsonBodyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IslandService {
    private final SessionDispatcher sessionDispatcher;

    @Autowired
    public IslandService(SessionDispatcher sessionDispatcher) {
        this.sessionDispatcher = sessionDispatcher;
    }

    public String info(String islandId) {
        Island island = sessionDispatcher.getIslandById(islandId);
        return JsonBodyGenerator.build(new IslandPresentator(island));
    }

    public String summary(String islandId) {
        Island island = sessionDispatcher.getIslandById(islandId);
        return JsonBodyGenerator.build(new FieldsSummaryPresentator(island));
    }
}
