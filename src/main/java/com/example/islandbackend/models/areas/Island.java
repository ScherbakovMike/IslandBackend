package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Setter
public class Island {
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private static Integer defaultWidth = 20;
    private static Integer defaultHeight = 40;
    private static Random random = new Random();
    private final List<List<Field>> fields = new ArrayList<>();
    private String id;
    private Integer width;
    private Integer height;

    private Island() {
        this.width = defaultWidth;
        this.height = defaultHeight;
    }

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

}
