package com.example.islandbackend.models.animals;

import com.example.islandbackend.models.areas.Field;

public interface Moveable {
    void move(Field source, Field destination);
}
