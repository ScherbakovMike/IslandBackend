package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Field {

    private String id;
    private final List<AbstractEntity> entities = new ArrayList<>();

    protected static Field newInstance() {
        Field result = new Field();
        result.id = UUID.randomUUID().toString();
        return result;
    }
}
