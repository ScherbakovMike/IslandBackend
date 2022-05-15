package com.example.islandbackend.models.animals;

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
    public void die() {

    }

    @Override
    public void move() {

    }

    @Override
    public void reproduce() {

    }
}
