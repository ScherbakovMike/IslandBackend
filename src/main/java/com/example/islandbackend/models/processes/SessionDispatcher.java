package com.example.islandbackend.models.processes;

import com.example.islandbackend.models.areas.Island;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SessionDispatcher {
    private Map<String, Session> sessions = new HashMap<>();
    private Map<String, Step> steps = new HashMap<>();
    private Map<String, Island> islands = new HashMap<>();

    public Step getStepById(String stepId) {
        return steps.get(stepId);
    }

    public Session getSessionById(String sessionId) {
        return sessions.get(sessionId);
    }

    public Island getIslandById(String islandId) {
        return islands.get(islandId);
    }
}
