package com.example.islandbackend.presentators;

import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldsSummaryPresentator extends BasePresentator {

    @JsonIgnore
    private final Step step;

    public FieldsSummaryPresentator(Island island) {
        super(island.getCurrentStep().getSession());
        this.island = island;
        this.step = island.getCurrentStep();
    }

    @JsonIgnore
    private final Island island;

    @JsonProperty("stepId")
    String getStepId() {
        return step.getId();
    }

    @JsonProperty("islandId")
    String getIslandId() {
        return island.getId();
    }

    @JsonProperty("stats")
    Map<String, Long> entityStats() {

//        var statByFields = island.getFields()
//                .stream()
//                .flatMap(Collection::stream)
//                .collect()
        return island.getFields().stream()
                .flatMap(Collection::stream)
                .flatMap(field -> field.getEntities().stream())
                .collect(Collectors.groupingBy(
                        entity -> entity.getClass().getSimpleName(), Collectors.counting()));
    }
}
