package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.animals.AbstractEntity;
import com.example.islandbackend.models.animals.Animal;
import com.example.islandbackend.models.animals.plants.Plant;
import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import com.example.islandbackend.models.processes.Step;
import com.example.islandbackend.presentators.JsonBodyGenerator;
import com.example.islandbackend.threadhelpers.CallableWithArgument;
import com.example.islandbackend.threadhelpers.CallableWithTwoArguments;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.islandbackend.models.characteristics.IslandCharacteristics.defaultHeight;
import static com.example.islandbackend.models.characteristics.IslandCharacteristics.defaultWidth;

@Getter
@Setter
public class Island {

    private ExecutorService executor;
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
                row.add(Field.newInstance(new Position(j, i)));
            }
            result.fields.add(row);
        }
        result.populate();
        return result;
    }

    private List<Class<? extends AbstractEntity>> kindsOfEntities = CharacteristicsHelpers.kindsOfEntities();
    private static final Logger logger = LoggerFactory.getLogger(Island.class);
    private ConcurrentHashMap<AbstractEntity, Boolean> movedElements = new ConcurrentHashMap<>();

    private void initExecutor() {
        executor = Executors.newCachedThreadPool();
    }

    private void waitResult() {
        if (executor instanceof ThreadPoolExecutor threadPoolExecutor) {
            while ((threadPoolExecutor.getTaskCount() - threadPoolExecutor.getCompletedTaskCount()) > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(this.getClass().getSimpleName(), e);
                }
            }
        }
    }

    protected void populate() {
        Instant now = Instant.now();
        initExecutor();
        kindsOfEntities.forEach(kind -> executeOnEachField(this::fillTheFieldByEntities, kind));
        waitResult();
        executor.shutdown();
        long executionTime = Instant.now().getEpochSecond() - now.getEpochSecond();
        String eventName = String.format("%s: populate() time: %d s", this.getClass().getSimpleName(), executionTime);
        logger.info(eventName);
    }

    public void reduceSatiety() {
        Instant now = Instant.now();
        initExecutor();
        executeOnEachField(this::reduceSatietyOnField);
        waitResult();
        executor.shutdown();
        long executionTime = Instant.now().getEpochSecond() - now.getEpochSecond();
        String eventName = String.format("%s: reduceSatiety() time: %d s", this.getClass().getSimpleName(), executionTime);
        logger.info(eventName);
    }

    public void reproduce() {
        Instant now = Instant.now();
        initExecutor();
        executeOnEachField(this::reproduceOnField);
        waitResult();
        executor.shutdown();
        long executionTime = Instant.now().getEpochSecond() - now.getEpochSecond();
        String eventName = String.format("%s: reproduce() time: %d s", this.getClass().getSimpleName(), executionTime);
        logger.info(eventName);
    }

    public void feed() {
        Instant now = Instant.now();
        initExecutor();
        executeOnEachField(this::feedAnimalsOnField);
        waitResult();
        executor.shutdown();
        long executionTime = Instant.now().getEpochSecond() - now.getEpochSecond();
        String eventName = String.format("%s: feed() time: %d s", this.getClass().getSimpleName(), executionTime);
        logger.info(eventName);
    }

    public void move() {
        Instant now = Instant.now();
        initExecutor();
        this.movedElements.clear();
        executeOnEachField(this::moveAnimalsByField);
        waitResult();
        executor.shutdown();
        long executionTime = Instant.now().getEpochSecond() - now.getEpochSecond();
        String eventName = String.format("%s: move() time: %d s", this.getClass().getSimpleName(), executionTime);
        logger.info(eventName);
    }

    private List<? extends AbstractEntity> fillTheFieldByEntities(Class<? extends AbstractEntity> kind, Field field) {
        var defaultEntityCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        List<AbstractEntity> entitiesInField = field.getEntities();
        var characteristics = defaultEntityCharacteristics.get(kind);
        int count = random.nextInt(characteristics.getMaxOnTheCell());
        try {
            for (int i = 0; i < count; i++) {
                var entity = kind.getDeclaredConstructor().newInstance();
                entitiesInField.add(entity);
            }
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }

        return List.of();
    }

    private <T, V, R> List<Future<R>> executeOnEachField(BiFunction<T, V, R> function, T arg1) {
        List<Future<R>> resultList = new ArrayList<>();
        this.fields.forEach(fieldRow -> fieldRow.forEach(field -> {
            CallableWithTwoArguments<T, V, R> task = new CallableWithTwoArguments(function, arg1, field);
            resultList.add(executor.submit(task));
        }));
        return resultList;
    }

    private <T, R> List<Future<R>> executeOnEachField(Function<T, R> function) {
        List<Future<R>> resultList = new ArrayList<>();
        this.fields.forEach(fieldRow -> fieldRow.forEach(field -> {
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
            if (className == Plant.class) {
                return;
            }
            while (!queueOfEntity.isEmpty()) {

                var firstRival = queueOfEntity.poll();
                if (firstRival == null) break;
                var characteristics = defaultCharacteristics.get(className);

                if ((firstRival instanceof Animal animal) && (((Animal) firstRival).getSatiety() >= characteristics.getSatiatedWeight())) {
                    survivedEntities.add(animal);
                    continue;
                }
                LinkedHashMap<Class<? extends AbstractEntity>, Integer> probabilityOfBeingEaten = characteristics.getProbabilityOfBeingEatenByClassName();
                var foundSecondRival = false;
                for (int i = (probabilityOfBeingEaten.size() - 1); i >= 0 && !foundSecondRival; i--) {
                    var entry = ((Map.Entry<Class<? extends AbstractEntity>, Integer>) probabilityOfBeingEaten.entrySet()
                            .toArray()[i]);
                    var probability = entry.getValue();
                    if (probability > 0) {
                        var queueOfRival = queuesByKind.get(entry.getKey());
                        if (queueOfRival.isEmpty()) continue;
                        var secondRival = queueOfRival.poll();
                        foundSecondRival = true;
                        var dealth = (random.nextInt(100) + 1) <= probability;
                        if (dealth) {
                            survivedEntities.add(firstRival);
                            Double currentSatiety = ((Animal) firstRival).getSatiety();
                            Double weightSecondRival = defaultCharacteristics.get(secondRival.getClass()).getWeight();
                            Double maxSatiety = characteristics.getSatiatedWeight();
                            ((Animal) firstRival).setSatiety((currentSatiety + weightSecondRival) > maxSatiety
                                    ? maxSatiety
                                    : (currentSatiety + weightSecondRival));
                            secondRival.die();
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

    private List<? extends AbstractEntity> reduceSatietyOnField(Field field) {
        List<AbstractEntity> entitiesForRemove = new ArrayList<>();
        var defaultCharacteristics = CharacteristicsHelpers.defaultCharacteristics;

        field.getEntities().forEach(entity -> {
            if (entity instanceof Plant) return;

            var characteristics = defaultCharacteristics.get(entity.getClass());
            var animal = (Animal) entity;
            var currentSatiety = animal.getSatiety();
            var stepsWithZeroSatiety = animal.getStepsWithZeroSatiety();
            var movesForSurvival = characteristics.getMovesForSurvival();
            var satiatedWeight = characteristics.getSatiatedWeight();
            var satietyDecreasePerStepPercent = characteristics.getSatietyDecreasePerStepPercent().doubleValue();
            if (currentSatiety == 0) {
                animal.setStepsWithZeroSatiety(stepsWithZeroSatiety + 1);
                if (movesForSurvival <= animal.getStepsWithZeroSatiety()) {
                    animal.die();
                    entitiesForRemove.add(animal);
                }
            } else {
                var newSatiety = Math.max(0, currentSatiety - satiatedWeight * satietyDecreasePerStepPercent / 100.0);
                animal.setSatiety(newSatiety);
            }
        });

        entitiesForRemove.forEach(entity -> field.getEntities().remove(entity));
        return entitiesForRemove;
    }

    private List<? extends AbstractEntity> reproduceOnField(Field field) {
        List<AbstractEntity> newEntities = new ArrayList<>();

        var defaultCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        var listsByKind = field.getEntities()
                .stream()
                .collect(Collectors.groupingBy(AbstractEntity::getClass));
        listsByKind.forEach((className, listOfEntity) -> {
            var characteristics = defaultCharacteristics.get(className);
            var probabilityOfReproduction = characteristics.getProbabilityOfReproduction();
            var countOfCubs = characteristics.getCountOfCubs();
            var countOfNewEntitiesProbable = Math.round(Integer.valueOf(listOfEntity.size()).doubleValue() * countOfCubs.doubleValue() * probabilityOfReproduction.doubleValue() / 100.0);
            var maxCountOfEntitiesOnFieldByKind = characteristics.getMaxOnTheCell();
            var countOfNewEntities = Math.min(countOfNewEntitiesProbable, maxCountOfEntitiesOnFieldByKind);
            for (int i = 0; i < countOfNewEntities; i++) {
                try {
                    newEntities.add(className.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    logger.error(this.getClass().getSimpleName(), e);
                    throw new RuntimeException(e);
                }
            }
        });

        field.getEntities().addAll(newEntities);
        return newEntities;
    }

    private Field getFieldByPosition(Position position) {
        return this.fields.get(position.getY()).get(position.getX());

    }

    private long countAnimalsOnFieldByKind(Field field, Class<? extends AbstractEntity> kind) {
        return field.getEntities().stream().filter(entity -> entity.getClass() == kind).count();
    }

    private Field getAvailableFieldForMoving(Field sourceField, Class<? extends AbstractEntity> kind) {
        var defaultCharacteristics = CharacteristicsHelpers.defaultCharacteristics;
        var characteristics = defaultCharacteristics.get(kind);
        var maxRadius = characteristics.getMovesForSurvival();
        var maxOnTheCell = characteristics.getMaxOnTheCell();
        int radius = 1;
        while (radius <= maxRadius) {
            Position curPosition = sourceField.getPosition();
            List<Field> variants = new ArrayList<>();
            Field fieldVariant = getFieldByPosition(new Position(curPosition.getX() - radius, curPosition.getY()));
            if (fieldVariant != null) {
                variants.add(fieldVariant);
            }
            fieldVariant = getFieldByPosition(new Position(curPosition.getX() + radius, curPosition.getY()));
            if (fieldVariant != null) {
                variants.add(fieldVariant);
            }
            fieldVariant = getFieldByPosition(new Position(curPosition.getX(), curPosition.getY() - radius));
            if (fieldVariant != null) {
                variants.add(fieldVariant);
            }
            fieldVariant = getFieldByPosition(new Position(curPosition.getX(), curPosition.getY() + radius));
            if (fieldVariant != null) {
                variants.add(fieldVariant);
            }
            Collections.shuffle(variants);
            if (!variants.isEmpty()) {
                for (Field field : variants) {
                    if ((countAnimalsOnFieldByKind(field, kind) + 1) <= maxOnTheCell) {
                        return field;
                    }
                }
            }
            radius++;
        }
        return null;
    }

    private List<? extends AbstractEntity> moveAnimalsByField(Field field) {
        var listsByKind = field.getEntities()
                .stream()
                .filter(entity -> entity.getClass() != Plant.class)
                .collect(Collectors.groupingBy(AbstractEntity::getClass));
        listsByKind.forEach((kind, listOfEntity) -> listOfEntity.forEach((entity) ->
        {
            if (movedElements.containsKey(entity)) {
                return;
            }
            Field fieldToMove = getAvailableFieldForMoving(field, kind);
            if (fieldToMove == null) {
                return;
            }
            field.getEntities().remove(entity);
            fieldToMove.getEntities().add(entity);
            movedElements.put(entity, true);
        }));
        return Collections.emptyList();
    }

    public Map<String, Map<String, Long>> entityStatsByFields() {
        return this.getFields().stream().flatMap(Collection::stream)
                .collect(HashMap::new,
                        (r, field) -> r.put(
                                JsonBodyGenerator.build(field),
                                field.getEntities().stream()
                                        .collect(
                                                Collectors.groupingBy(entity -> entity.getClass().getSimpleName(), Collectors.counting())
                                        )
                        ), (r, r1) -> {
                        });
    }

    public Map<Class<? extends AbstractEntity>, Long> entityState() {
        return this.getFields().stream()
                .flatMap(Collection::stream)
                .flatMap(field -> field.getEntities().stream())
                .collect(Collectors.groupingBy(AbstractEntity::getClass, Collectors.counting()));
    }

    public Map<String, String> compareStates(
            Map<Class<? extends AbstractEntity>, Long> anotherState
    ) {
        Map<String, String> result = new HashMap<>();
        Map<Class<? extends AbstractEntity>, Long> currentState = entityState();

        MapDifference<Class<? extends AbstractEntity>, Long> difference = Maps.difference(currentState, anotherState);
        difference.entriesOnlyOnLeft().forEach((aClass, aLong) ->
                result.put(aClass.getSimpleName(), "bornt: " + aLong));
        difference.entriesOnlyOnLeft().forEach((aClass, aLong) ->
                result.put(aClass.getSimpleName(), "completely died: " + aLong));
        difference.entriesDiffering().forEach((aClass, longValueDifference) ->
                result.put(aClass.getSimpleName(), String.valueOf(longValueDifference.leftValue() - longValueDifference.rightValue())));
        return result;
    }
}
