package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StepPresentator extends BasePresentator {

    public StepPresentator(Step step) {
        super(step.getSession());
        this.step = step;
    }

    @JsonIgnore
    private final Step step;

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
}
