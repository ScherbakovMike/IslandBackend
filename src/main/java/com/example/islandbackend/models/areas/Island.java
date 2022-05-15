package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.animals.Animal;
import com.example.islandbackend.models.animals.plants.Plant;
import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import com.example.islandbackend.models.processes.Step;
import com.example.islandbackend.threadhelpers.CallableWithArgument;
import com.example.islandbackend.threadhelpers.CallableWithTwoArguments;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private List<Class<? extends AbstractEntity>> kindsOfEntities = CharacteristicsHelpers.kindsOfEntities();

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
        kindsOfEntities.forEach(kind -> executeOnEachField(this::fillTheFieldByEntities, kind));
        waitResult();
    }

    private List<? extends AbstractEntity> fillTheFieldByEntities(Class<? extends AbstractEntity> kind, Field field) {

        var defaultEntityCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        List<AbstractEntity> entitiesInField = field.getEntities();
        var entityCharacteristics = defaultEntityCharacteristics.get(kind);
        int count = random.nextInt(entityCharacteristics.getMaxOnTheCell());
        try {
            for (int i = 0; i < count; i++) {
                var entity = kind.getDeclaredConstructor().newInstance();
                entitiesInField.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entitiesInField;
    }

    private <T, V, R> List<Future<R>> executeOnEachField(BiFunction<T, V, R> function, T arg1) {

        List<Future<R>> resultList = new ArrayList<>();
        this.fields.forEach(fieldRow ->
                fieldRow.forEach(field -> {
                    CallableWithTwoArguments<T, V, R> task = new CallableWithTwoArguments(function, arg1, field);
                    resultList.add(executor.submit(task));
                }));
        return resultList;
    }

    private <T, R> List<Future<R>> executeOnEachField(Function<T, R> function) {

        List<Future<R>> resultList = new ArrayList<>();
        this.fields.forEach(fieldRow ->
                fieldRow.forEach(field -> {
                    CallableWithArgument<T, R> task = new CallableWithArgument(function, field);
                    resultList.add(executor.submit(task));
                }));
        return resultList;
    }

    private List<? extends AbstractEntity> feedAnimalsOnField(Field field) {
        List<AbstractEntity> survivedEntities = new ArrayList<>();

        var defaultCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        var queuesByKind = new HashMap<Class<? extends AbstractEntity>, ConcurrentLinkedQueue<AbstractEntity>>();
        field.getEntities()
                .stream()
                .collect(Collectors.groupingBy(AbstractEntity::getClass))
                .forEach((className, entityList) -> queuesByKind.put(className, new ConcurrentLinkedQueue<>(entityList)));
        queuesByKind.forEach((className, queueOfEntity) -> {
            if (className != Plant.class) {
                while (!queueOfEntity.isEmpty()) {

                    var firstRival = queueOfEntity.poll();
                    if (firstRival == null)
                        break;
                    var characteristics = defaultCharacteristics.get(className);

                    if ((firstRival instanceof Animal) &&
                            (((Animal) firstRival).getSatiety() >= characteristics.getSatiatedWeight())) {
                        survivedEntities.add(firstRival);
                        continue;
                    }

                    LinkedHashMap<Class<? extends AbstractEntity>, Integer> probabilityOfBeingEaten =
                            characteristics.getProbabilityOfBeingEatenByClassName();
                    var foundSecondRival = false;
                    for (int i = (probabilityOfBeingEaten.size() - 1); i >= 0 && !foundSecondRival; i--) {
                        var entry = ((Map.Entry<Class<? extends AbstractEntity>, Integer>)
                                probabilityOfBeingEaten.entrySet().toArray()[i]);
                        var probability = entry.getValue();
                        if (probability > 0) {
                            var queueOfRival = queuesByKind.get(entry.getKey());
                            if (queueOfRival.isEmpty())
                                continue;
                            var secondRival = queueOfRival.poll();
                            foundSecondRival = true;
                            var dealth = (random.nextInt(100) + 1) <= probability;
                            if (dealth) {
                                survivedEntities.add(firstRival);
                                Double currentSatiety = ((Animal) firstRival).getSatiety();
                                Double weightSecondRival = defaultCharacteristics.get(secondRival.getClass()).getWeight();
                                Double maxSatiety = characteristics.getSatiatedWeight();
                                ((Animal) firstRival).setSatiety(
                                        (currentSatiety + weightSecondRival) > maxSatiety
                                                ? maxSatiety : (currentSatiety + weightSecondRival)
                                );
                                secondRival.die();
                            }
                        }
                    }
                }
            }
        });
        if (queuesByKind.containsKey(Plant.class)) {
            var plantsQueue = queuesByKind.get(Plant.class);
            survivedEntities.addAll(plantsQueue);
        }
        field.getEntities().clear();
        field.getEntities().addAll(survivedEntities);
        return field.getEntities();
    }

    protected void reproduce() {

    }

    public void feed() {
        executeOnEachField(this::feedAnimalsOnField);
        waitResult();
    }

    private void waitResult() {
        while ((executor.getTaskCount() - executor.getCompletedTaskCount()) > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    protected void move() {

    }


}
