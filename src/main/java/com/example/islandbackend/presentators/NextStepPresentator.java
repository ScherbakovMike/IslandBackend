package com.example.islandbackend.presentators;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class NextStepPresentator extends BasePresentator {

    @JsonIgnore
    private final Step step;
    @JsonIgnore
    private final Map<Class<? extends AbstractEntity>, Long> previousIslandState;

    public NextStepPresentator(Step step, Map<Class<? extends AbstractEntity>, Long> previousIslandState) {
        super(step.getSession());
        this.step = step;
        this.previousIslandState = previousIslandState;
    }

    @JsonProperty("stepId")
    String getStepId() {
        return step.getId();
    }

    @JsonProperty("stepNumber")
    Integer getStepNumber() {
        return step.getStepNumber();
    }

    @JsonProperty("islandId")
    String getIslandIdNumber() {
        return step.getIslandState().getId();
    }

    @JsonProperty("changes")
    Map<String, String> getChanges() {
        return step.getIslandState().compareStates(previousIslandState);
    }
}
