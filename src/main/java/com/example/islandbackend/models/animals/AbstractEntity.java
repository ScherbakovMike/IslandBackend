package com.example.islandbackend.models.animals;

import com.example.islandbackend.models.characteristics.EntityCharacteristics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntity implements Reproducible, Dieable {
    private String id;
    private Boolean isAlive = true;
    private EntityCharacteristics characteristics;

}
