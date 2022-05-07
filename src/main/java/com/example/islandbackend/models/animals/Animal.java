package com.example.islandbackend.models.animals;

public abstract class Animal extends AbstractEntity implements Moveable, Dieable {

    private Characteristics.EntityCharacteristics characteristics;

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
