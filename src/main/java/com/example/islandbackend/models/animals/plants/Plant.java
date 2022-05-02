package com.example.islandbackend.models.animals.plants;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.animals.Dieable;
import com.example.islandbackend.models.animals.Reproducible;

class Plant extends AbstractEntity implements Reproducible, Dieable {

    @Override
    public void die() {

    }

    @Override
    public void reproduce() {

    }
}
