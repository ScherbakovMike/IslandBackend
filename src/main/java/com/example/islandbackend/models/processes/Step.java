package com.example.islandbackend.models.processes;

import com.example.islandbackend.models.areas.Island;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public
class Step {
    private String id = UUID.randomUUID().toString();

    private Session session;

    private int stepNumber;

    private Island islandState;

}
