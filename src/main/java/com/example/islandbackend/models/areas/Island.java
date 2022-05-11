package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import com.example.islandbackend.models.processes.Step;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Setter
public class Island {
    private static Integer defaultWidth = 20;
    private static Integer defaultHeight = 40;
    private static Random random = new Random();
    private final List<List<Field>> fields = new ArrayList<>();
    private String id = UUID.randomUUID().toString();
    private Integer width = defaultWidth;
    private Integer height = defaultHeight;
    private Step currentStep;

    public static Island newInstance() {
        Island result = new Island();
        for (int i = 0; i < defaultHeight; i++) {
            List<Field> row = new ArrayList<>();
            for (int j = 0; j < defaultWidth; j++) {
                row.add(Field.newInstance());
            }
            result.fields.add(row);
        }
        result.populate();
        return result;
    }

    protected void populate() {
        var executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        var kindsOfEntities = CharacteristicsHelpers.kindsOfEntities();

        List<AbstractEntity> entities = new ArrayList<>();
        List<Future<List<? extends AbstractEntity>>> futureTasks = new ArrayList<>();
        kindsOfEntities.forEach(kind ->
                this.fields.forEach(
                        fieldRow ->
                                fieldRow.forEach(
                                        field ->
                                                futureTasks.add(executor.submit(() -> populateFieldByKind(kind, field))))
                ));

        futureTasks.forEach(task -> {
            try {
                entities.addAll(task.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        executor.shutdown();
    }

    private List<? extends AbstractEntity> populateFieldByKind(Class<? extends AbstractEntity> kind, Field field)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var defaultEntityCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        var entityCharacteristics = defaultEntityCharacteristics.get(kind);
        int count = random.nextInt(entityCharacteristics.getMaxOnTheCell());
        List<AbstractEntity> entitiesInField = field.getEntities();
        for (int i = 0; i < count; i++) {
            var entity = kind.getDeclaredConstructor().newInstance();
            entity.setCharacteristics(entityCharacteristics);
            entitiesInField.add(entity);
        }
        return entitiesInField;
    }

    protected void reproduce() {

    }

    protected void feed() {

    }

    protected void move() {

    }
}
