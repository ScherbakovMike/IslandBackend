package com.example.islandbackend.models.processes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash("Session")
@RequiredArgsConstructor
public class Session {
    @Getter(AccessLevel.PROTECTED)
    private String id;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Long startTime;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Long endTime;

    private Step currentStep;

    public static Session newInstance() {
        Session result = new Session();
        result.setStartTime(Instant.now().toEpochMilli());
        result.currentStep = Step.newInstance(result);
        return new Session();
    }
}
