package com.example.islandbackend.models.animals;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractEntity implements Reproducible, Dieable {
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Boolean isAlive = true;
}
