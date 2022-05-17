package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Field {

    private String id = UUID.randomUUID().toString();
    private final List<AbstractEntity> entities = new CopyOnWriteArrayList<>();

    protected static Field newInstance() {
        return new Field();
    }
}
