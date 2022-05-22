package com.example.islandbackend.presentators;

import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IslandPresentator extends BasePresentator {

    @JsonIgnore
    private final Step step;

    @JsonIgnore
    private final Island island;

    public IslandPresentator(Island island) {
        super(island.getCurrentStep().getSession());
        this.island = island;
        this.step = island.getCurrentStep();
        this.session = island.getCurrentStep().getSession();
    }

    @JsonProperty("sessionId")
    String getSessionId() {
        return session.getId();
    }

    @JsonProperty("stepId")
    String getStepId() {
        return step.getId();
    }

    @JsonProperty("islandId")
    String getIslandId() {
        return island.getId();
    }

    @JsonProperty("width")
    Integer getWidth() {
        return island.getWidth();
    }

    @JsonProperty("height")
    Integer getHeight() {
        return island.getHeight();
    }
}
