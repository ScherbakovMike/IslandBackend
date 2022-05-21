package com.example.islandbackend.presentators;

import com.example.islandbackend.models.areas.Field;
import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;
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
    Map<String, Map<String, Long>> entityStats() {
        var entitiesByFieldAndKind = island.getFields().stream().flatMap(Collection::stream)
                .collect(this::supplier, this::accumulator, this::combiner);
        return entitiesByFieldAndKind;
    }

    private void accumulator(Map<String, Map<String, Long>> r, Field field) {
        r.put(
                field.getId(),
                field.getEntities().stream()
                        .collect(
                                Collectors.groupingBy(entity -> entity.getClass().getSimpleName(), Collectors.counting())
                        )
        );
    }

    private <R> void combiner(R r, R r1) {
    }

    private <R> Map<String, Map<String, Long>> supplier() {
        return new HashMap<>();
    }
}
