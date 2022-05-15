package com.example.islandbackend.models.animals;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractEntity implements Reproducible, Dieable {
    private String id = UUID.randomUUID().toString();
    private Boolean isAlive = true;
}
