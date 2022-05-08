package com.example.islandbackend.models.characteristics;

import com.example.islandbackend.models.animals.Dieable;
import com.example.islandbackend.models.animals.herbivores.*;
import com.example.islandbackend.models.animals.plants.Plant;
import com.example.islandbackend.models.animals.predators.*;

import java.util.List;

public class CharacteristicsHelpers {
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

    public static Boolean loadDefaultEntityCharacteristicsFromFiles() {
        return true;
    }


    //    protected static class ProbabilityOfBeingEaten {
//        private static final Map<Class<Dieable>, ProbabilityParams> map = new HashMap<>();
//
//        protected static Integer getProbability(Dieable who, Dieable whom) {
//            Optional<ProbabilityParams> probability = map.entrySet().stream()
//                    .filter(dieableProbabilityParamsEntry ->
//                            dieableProbabilityParamsEntry.getKey() == who.getClass()
//                                    && dieableProbabilityParamsEntry.getValue().whom == whom.getClass())
//                    .map(Map.Entry::getValue)
//                    .findFirst();
//            return probability.orElseThrow(() ->
//                    new RuntimeException("Probability not found for pair: who: %s, whom: %s"
//                            .formatted(who.getClass().getSimpleName(), whom.getClass().getSimpleName())))
//                    .probability;
//        }
//
//        private record ProbabilityParams(Class<Dieable> who, Class<Dieable> whom, Integer probability) {
//        }
//
//    }
}
