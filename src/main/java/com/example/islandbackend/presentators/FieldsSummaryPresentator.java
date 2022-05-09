package com.example.islandbackend.presentators;

import com.example.islandbackend.models.areas.Island;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldsSummaryPresentator implements ObjectPresentator {

    @JsonIgnore
    private final Island island;

    public FieldsSummaryPresentator(Island island) {
        this.island = island;
    }

    @JsonProperty("stats")
    Map<String, Long> entityStats() {
        return island.getFields().stream()
                .flatMap(Collection::stream)
                .flatMap(field -> field.getEntities().stream())
                .collect(Collectors.groupingBy(
                        entity -> entity.getClass().getSimpleName(), Collectors.counting()));
    }
}
