package com.example.islandbackend.presentators;

import com.example.islandbackend.models.areas.Island;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IslandPresentator implements ObjectPresentator {

    @JsonIgnore
    Island island;

    public IslandPresentator(Island island) {
        this.island = island;
    }

    Integer getWidth() {
        return island.getWidth();
    }

    Integer getHeight() {
        return island.getHeight();
    }


}
