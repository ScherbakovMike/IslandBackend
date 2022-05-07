package com.example.islandbackend.models.animals;

import com.example.islandbackend.models.animals.herbivores.*;
import com.example.islandbackend.models.animals.plants.Plant;
import com.example.islandbackend.models.animals.predators.*;

import java.util.HashMap;
import java.util.List;
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

    public static List<Class<? extends Dieable>> kindsOfDieable() {
        return List.of(Caterpillar.class,
                Cow.class,
                Deer.class,
                Duck.class,
                Goat.class,
                Hamster.class,
                Hare.class,
                Horse.class,
                Kangaroo.class,
                Sheep.class,
                Bear.class,
                Eagle.class,
                Fox.class,
                Snake.class,
                Wolf.class,
                Plant.class);
    }

    public static class ProbabilityOfBeingEaten {
        private static final Map<Class<Dieable>, ProbabilityParams> map = new HashMap<>();

        public static Integer getProbability(Dieable who, Dieable whom) {
            Optional<ProbabilityParams> probability = map.entrySet().stream()
                    .filter(dieableProbabilityParamsEntry ->
                            dieableProbabilityParamsEntry.getKey() == who.getClass()
                                    && dieableProbabilityParamsEntry.getValue().whom == whom.getClass())
                    .map(Map.Entry::getValue)
                    .findFirst();
            return probability.orElseThrow(() ->
                    new RuntimeException("Probability not found for pair: who: %s, whom: %s"
                            .formatted(who.getClass().getSimpleName(), whom.getClass().getSimpleName())))
                    .probability;
        }

        private record ProbabilityParams(Class<Dieable> who, Class<Dieable> whom, Integer probability) {
        }

    }
}