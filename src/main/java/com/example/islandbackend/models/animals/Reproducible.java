package com.example.islandbackend.models.animals;

import java.lang.reflect.InvocationTargetException;

public interface Reproducible {
    static AbstractEntity reproduce(Class<? extends AbstractEntity> className)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return className.getDeclaredConstructor().newInstance();
    }
}
