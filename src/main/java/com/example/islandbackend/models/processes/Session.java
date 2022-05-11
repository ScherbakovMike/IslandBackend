package com.example.islandbackend.models.processes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class Session {
    @Getter
    @Setter
    private String id = UUID.randomUUID().toString();

    @Getter
    @Setter
    private Date startTime = new Date();

    @Getter
    @Setter
    private Date endTime;

    @Getter
    @Setter
    private Step currentStep;
}
