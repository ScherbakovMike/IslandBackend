package com.example.islandbackend.models.processes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Session {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Long startTime;

    @Getter
    @Setter
    private Long endTime;

    @Getter
    @Setter
    private Step currentStep;
}
