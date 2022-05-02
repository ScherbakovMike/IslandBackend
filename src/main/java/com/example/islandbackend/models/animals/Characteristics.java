package com.example.islandbackend.models.animals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Characteristics {

    private Characteristics() {
    }

    public static class EntityCharacteristics {

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

    public static class ProbabilityOfBeingEaten {
        private static final Map<Dieable, ProbabilityParams> map = new HashMap<>();

        public static Integer getProbability(Dieable who, Dieable whom) {
            Optional<ProbabilityParams> probability = map.entrySet().stream()
                    .filter(dieableProbabilityParamsEntry ->
                            dieableProbabilityParamsEntry.getKey() == who
                                    && dieableProbabilityParamsEntry.getValue().whom == whom)
                    .map(Map.Entry::getValue)
                    .findFirst();
            return probability.orElseThrow(() ->
                    new RuntimeException("Probability not found for pair: who: %s, whom: %s"
                            .formatted(who.toString(), whom.toString())))
                    .probability;
        }

        private record ProbabilityParams(Dieable who, Dieable whom, Integer probability) {
        }


    }
}