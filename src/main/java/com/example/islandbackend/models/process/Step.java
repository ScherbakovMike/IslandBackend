package com.example.islandbackend.models.process;

import com.example.islandbackend.models.areas.Island;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@RedisHash("Step")
public class Step {
    private @Id
    @GeneratedValue
    Long stepId;
    private Long sessionId;
    private int stepNumber;
    private Island islandState;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
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
