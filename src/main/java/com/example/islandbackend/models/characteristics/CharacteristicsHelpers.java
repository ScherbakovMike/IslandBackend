package com.example.islandbackend.models.characteristics;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.animals.herbivores.*;
import com.example.islandbackend.models.animals.plants.Plant;
import com.example.islandbackend.models.animals.predators.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CharacteristicsHelpers {

    private static final String defaultCharacteristicsPropertiesFileName = "DefaultCharacteristicsProperties.json";
    private static final Logger logger = LoggerFactory.getLogger(CharacteristicsHelpers.class);
    public static final Map<Class<? extends AbstractEntity>, EntityCharacteristics> defaultCharacteristics =
            loadDefaultEntityCharacteristicsFromFiles();

    private CharacteristicsHelpers() {
    }

    public static List<Class<? extends AbstractEntity>> kindsOfEntities() {
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

    public static Map<Class<? extends AbstractEntity>, EntityCharacteristics> loadDefaultEntityCharacteristicsFromFiles() {
        File fileOfProperties = new File(Objects.requireNonNull(
                        CharacteristicsHelpers.class.getClassLoader().getResource(defaultCharacteristicsPropertiesFileName))
                .getFile());
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<EntityCharacteristics> entityCharacteristics =
                    Arrays.asList(mapper.readValue(fileOfProperties, EntityCharacteristics[].class));
            resolveClassNameForEntityCharacteristics(entityCharacteristics);

            Map<Class<? extends AbstractEntity>, EntityCharacteristics> resultToProcess =
                    entityCharacteristics.stream()
                            .collect(Collectors.toMap(EntityCharacteristics::getClassName, Function.identity()));

            resultToProcess.forEach((className, characteristics) -> {
                var unsortedMap = characteristics.getProbabilityOfBeingEatenByClassName();
                var sortedMap = new LinkedHashMap<Class<? extends AbstractEntity>, Integer>();
                unsortedMap.entrySet().stream().sorted(
                        Map.Entry.comparingByValue()).forEach(entry ->
                        sortedMap.put(entry.getKey(), entry.getValue()));
                characteristics.setProbabilityOfBeingEatenByClassName(sortedMap);
            });

            return resultToProcess;
        } catch (Exception e) {
            logger.error(CharacteristicsHelpers.class.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static void resolveClassNameForEntityCharacteristics(List<EntityCharacteristics> entityCharacteristics) {
        List<Class<? extends AbstractEntity>> kindsOfEntities = kindsOfEntities();

        entityCharacteristics.forEach(characteristic ->
                characteristic.setClassName(kindsOfEntities.stream()
                        .filter(it -> (it.getSimpleName() + ".class").equals(characteristic.getSimpleClassName()))
                        .findFirst()
                        .orElseThrow())
        );

        entityCharacteristics.forEach(characteristic ->
                characteristic.getProbabilityOfBeingEatenBySimpleClassName()
                        .forEach((simpleClassName, probability) -> {
                                    Class<? extends AbstractEntity> className = kindsOfEntities.stream()
                                            .filter(it -> (it.getSimpleName() + ".class").equals(simpleClassName))
                                            .findFirst()
                                            .orElseThrow();
                                    characteristic.getProbabilityOfBeingEatenByClassName().put(className, probability);
                                }
                        )
        );
    }
}
