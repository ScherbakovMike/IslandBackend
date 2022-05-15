package com.example.islandbackend.models.characteristics;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class EntityCharacteristics {

    private String simpleClassName;
    private Double weight;
    private Integer maxOnTheCell;
    private Integer movementSpeed;
    private Double satiatedWeight;
    private Integer movesForSurvival;
    private Integer satietyDecreasePerStepPercent;
    private LinkedHashMap<Class<? extends AbstractEntity>, Integer> probabilityOfBeingEatenByClassName = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> probabilityOfBeingEatenBySimpleClassName = new LinkedHashMap<>();

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