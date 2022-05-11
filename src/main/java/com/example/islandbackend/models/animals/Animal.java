package com.example.islandbackend.models.animals;

import lombok.Getter;
import lombok.Setter;

public abstract class Animal extends AbstractEntity implements Moveable, Dieable {

    @Getter @Setter
    private Double satiety;

    @Getter @Setter
    private Integer stepsWithZeroSatiety;

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
