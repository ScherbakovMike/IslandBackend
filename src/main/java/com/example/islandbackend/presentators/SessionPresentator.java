package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;

@RequiredArgsConstructor
public class SessionPresentator implements ObjectPresentator {

    @JsonIgnore
    private final Session session;

    @JsonProperty("id")
    String getId() {
        return session.getId();
    }

    @JsonProperty("startTime")
    String getStartTime() {
        DateFormat dateFormat = StringFormats.DATETIME;
        return dateFormat.format(session.getStartTime());
    }

    @JsonProperty("endTime")
    String getEndTime() {
        if (session.getEndTime() == null)
            return "";
        else {
            DateFormat dateFormat = StringFormats.DATETIME;
            return dateFormat.format(session.getEndTime());
        }
    }

    @JsonProperty("currentStep")
    String getCurrentStepId() {
        return session.getCurrentStep().getId();
    }

    @JsonProperty("islandId")
    String getIslandId() {
        return session.getCurrentStep().getIslandState().getId();
    }
}
