package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("Field")
class Field {
    private final List<AbstractEntity> entities = new ArrayList<>();

    protected static Field newInstance() {
        return new Field();
    }
}
