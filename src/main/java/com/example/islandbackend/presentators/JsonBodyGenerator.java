package com.example.islandbackend.presentators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyGenerator {
    private JsonBodyGenerator() {
    }

    public static String build(ObjectPresentator presentator) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(presentator);
    }
}
