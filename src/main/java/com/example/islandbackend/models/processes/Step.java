package com.example.islandbackend.models.processes;

import com.example.islandbackend.models.areas.Island;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public
class Step {
    private String id;

    private Session session;

    private int stepNumber;

    private Island islandState;

}
