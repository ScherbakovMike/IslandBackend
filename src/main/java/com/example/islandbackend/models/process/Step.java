package com.example.islandbackend.models.process;

import com.example.islandbackend.models.areas.Island;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Step")
public class Step {
    private String id;
    private Long sessionId;
    private int stepNumber;
    private Island islandState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Island getIslandState() {
        return islandState;
    }

    public void setIslandState(Island islandState) {
        this.islandState = islandState;
    }
}
