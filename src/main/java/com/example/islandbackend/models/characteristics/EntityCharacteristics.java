package com.example.islandbackend.models.characteristics;

import com.example.islandbackend.models.animals.Dieable;

import java.util.Map;

public class EntityCharacteristics {

    private static Map<Class<? extends Dieable>, EntityCharacteristics> defaultEntityCharacteristics;
    /**
     * Weight of one animal, kg
     */
    Double weight;
    /**
     * The maximum number of animals of this species on one cage
     */
    Integer maxOnTheCell;
    /**
     * Movement speed, no more than, cells per turn
     */
    Integer movementSpeed;
    /**
     * How many kilograms of food does an animal need to be completely satiated?
     */
    Double satiatedWeight;
    /**
     * How many moves (cycles) an animal can live after the satiety bar drops to zero
     */
    Integer movesForSurvival;
    /**
     * Probability of being eaten by another animal
     */
    Map<Class<? extends Dieable>, Integer> probabilityOfBeingEaten;
        
    private EntityCharacteristics() {}

    public static Boolean loadDefaultEntityCharacteristics() {
        return CharacteristicsHelpers.loadDefaultEntityCharacteristicsFromFiles();
    }
}