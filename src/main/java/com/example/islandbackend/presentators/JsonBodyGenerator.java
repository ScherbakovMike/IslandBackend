package com.example.islandbackend.presentators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonBodyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(JsonBodyGenerator.class);

    private JsonBodyGenerator() {
    }

    public static String build(BasePresentator presentator) {
        try {
            ObjectMapper objectMapper = JsonMapper.builder().build();
            return objectMapper.writeValueAsString(presentator);
        } catch (JsonProcessingException e) {
            logger.error(JsonBodyGenerator.class.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static String build(Object object) {
        try {
            ObjectMapper objectMapper = JsonMapper.builder().build();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(JsonBodyGenerator.class.getName(), e);
            throw new RuntimeException(e);
        }
    }

}
