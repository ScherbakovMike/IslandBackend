package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
class Field {

    private String id;
    private final List<AbstractEntity> entities = new ArrayList<>();

    protected static Field newInstance() {
        return new Field();
    }
}
