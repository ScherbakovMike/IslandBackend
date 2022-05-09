package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StepPresentator implements ObjectPresentator {

    @JsonIgnore
    private final Step step;

    @JsonIgnore
    private final Session session;

    public StepPresentator(Step step) {
        this.step = step;
        this.session = step.getSession();
    }

    @JsonProperty("sessionId")
    String getSessionId() {
        return session.getId();
    }

    @JsonProperty("stepId")
    String getStepId() {
        return step.getId();
    }

    @JsonProperty("stepNumber")
    Integer getStepNumber() {
        return step.getStepNumber();
    }
}
