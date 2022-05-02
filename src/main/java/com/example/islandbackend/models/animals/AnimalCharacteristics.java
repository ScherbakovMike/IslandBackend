package com.example.islandbackend.models.animals;

public class AnimalCharacteristics {

    /**
     * Weight of one animal, kg
     */
    Double weight = 0.0;

    /**
     * The maximum number of animals of this species on one cage
     */
    Integer maxOnTheCell = 0;

    /**
     * Movement speed, no more than, cells per turn
     */
    Integer movementSpeed = 0;

    /**
     * How many kilograms of food does an animal need to be completely satiated?
     */
    Double satiatedWeight = 0.0;

    /**
     * How many moves (cycles) an animal can live after the satiety bar drops to zero
     */
    Integer movesForSurvival = 0;
}
