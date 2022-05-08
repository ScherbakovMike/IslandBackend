package com.example.islandbackend.models.animals;

import com.example.islandbackend.models.characteristics.EntityCharacteristics;

public abstract class Animal extends AbstractEntity implements Moveable, Dieable {

    private EntityCharacteristics characteristics;

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
