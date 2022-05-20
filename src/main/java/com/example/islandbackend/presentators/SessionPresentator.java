package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;

public class SessionPresentator extends BasePresentator {

    public SessionPresentator(Session session) {
        super(session);
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

    @JsonProperty("stepId")
    String getCurrentStepId() {
        return session.getCurrentStep().getId();
    }

    @JsonProperty("islandId")
    String getIslandId() {
        return session.getCurrentStep().getIslandState().getId();
    }
}
