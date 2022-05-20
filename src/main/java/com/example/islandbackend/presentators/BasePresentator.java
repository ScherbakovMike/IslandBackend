package com.example.islandbackend.presentators;

import com.example.islandbackend.models.processes.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BasePresentator {

    @JsonIgnore
    protected Session session;

    public BasePresentator(Session session) {
        this.session = session;
    }

    @JsonProperty("sessionId")
    String getId() {
        return session.getId();
    }
}
