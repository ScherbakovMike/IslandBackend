package com.example.islandbackend.models.processes;

import com.example.islandbackend.models.areas.Island;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Step")
@RequiredArgsConstructor
class Step {
    @Getter(AccessLevel.PROTECTED)
    private String id;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Session session;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int stepNumber;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Island islandState;

    protected static Step newInstance(Session session) {
        Step result = new Step();
        result.session = session;
        result.stepNumber = 0;
        result.islandState = Island.newInstance();
        return result;
    }
}
