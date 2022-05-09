package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionPresentator implements ObjectPresentator {

    @JsonIgnore
    private final Session session;

    public SessionPresentator(Session session) {
        this.session = session;
    }

    @JsonProperty("id")
    String getId() {
        return session.getId();
    }

    @JsonProperty("startTime")
    String getStartTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(new Date(session.getStartTime()));
    }
}
