package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Field {

    @JsonIgnore
    private final List<AbstractEntity> entities = new CopyOnWriteArrayList<>();
    @JsonProperty("id")
    private String id = UUID.randomUUID().toString();
    @JsonIgnore
    private Position position;

    public Field(Position position) {
        this.position = position;
    }

    protected static Field newInstance(Position position) {
        return new Field(position);
    }

    @JsonProperty("position")
    private String getJsonPosition() {
        return String.format("(%d,%d)", position.getY(), position.getX());
    }
}
