package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.Step;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StepPresentator implements ObjectPresentator {

    @JsonIgnore
    private final Step step;

    @JsonIgnore
    private final Session session;

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

    @JsonProperty("islandId")
    String getIslandIdNumber() {
        return step.getIslandState().getId();
    }
}
