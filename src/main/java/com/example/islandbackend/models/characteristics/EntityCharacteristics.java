package com.example.islandbackend.models.characteristics;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class EntityCharacteristics {

    /**
     * Probability of being eaten by another animal (by simle class name)
     */

    @Getter
    private final Map<String, Integer> probabilityOfBeingEatenBySimpleClassName = new HashMap<>();
    /**
     * Simple class name
     */
    @Getter
    @Setter
    private String simpleClassName;
    /**
     * Weight of one animal, kg
     */
    @Getter
    @Setter
    private Double weight;
    /**
     * The maximum number of animals of this species on one cage
     */
    @Getter
    @Setter
    private Integer maxOnTheCell;
    /**
     * Movement speed, no more than, cells per turn
     */
    @Getter
    @Setter
    private Integer movementSpeed;
    /**
     * How many kilograms of food does an animal need to be completely satiated?
     */
    @Getter
    @Setter
    private Double satiatedWeight;
    /**
     * How many moves (cycles) an animal can live after the satiety bar drops to zero
     */
    @Getter
    @Setter
    private Integer movesForSurvival;
    /**
     * Probability of being eaten by another animal
     */
    @Getter
    @Setter
    private Map<Class<? extends AbstractEntity>, Integer> probabilityOfBeingEatenByClassName = new HashMap<>();
    /**
     * Class name of entity
     */
    @Getter
    @Setter
    @JsonIgnore
    private Class<? extends AbstractEntity> className;

    public void setProbabilityOfBeingEatenBySimpleClassName(Object probabilityOfBeingEatenBySimpleClassName) {
        List<Map<String, Object>> probabilityOfBeingEatenByClassNameDto =
                (List<Map<String, Object>>) probabilityOfBeingEatenBySimpleClassName;
        probabilityOfBeingEatenByClassNameDto.forEach(dto ->
                this.probabilityOfBeingEatenBySimpleClassName.put(
                        dto.get("simpleClassName").toString(), (Integer) dto.get("probability")
                ));
    }
}