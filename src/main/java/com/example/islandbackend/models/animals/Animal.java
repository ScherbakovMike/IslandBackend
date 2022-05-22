package com.example.islandbackend.models.animals;

import com.example.islandbackend.models.areas.Field;
import lombok.Getter;
import lombok.Setter;

public abstract class Animal extends AbstractEntity implements Moveable {

    @Getter
    @Setter
    private Double satiety = 0.0;

    @Getter
    @Setter
    private Integer stepsWithZeroSatiety = 0;

    @Override
    public void move(Field source, Field destination) {
        source.getEntities().remove(this);
        destination.getEntities().add(this);
    }
}
