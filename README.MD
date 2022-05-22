## The project "Island"

**Introduction**

There are a lot of entities on the sea island : animals and plants.

Animals can be *herbivores* and *predators*.  
The following types of animals can be *Herbivores*:
- Caterpillar
- Cow
- Deer
- Duck
- Goat
- Hamster
- Hare
- Horse
- Kangaroo
- Sheep

The following types of animals can be *Predators*:
- Bear
- Eagle
- Fox
- Snake
- Wolf

Plants can only be of one kind - simple plants.  
Animals have the ability to **move**, **feed** and **reproduce**.  
Plants can only reproduce themselves.  
There is a different probability of being eaten by another kind of animal. Various probabilities by kinds are presented in the table below:

|                 | Wolf     | Snake | Fox | Bear | Eagle | Horse | Deer | Hare | Hamster | Goat | Sheep | Kangaroo | Cow | Duck | Caterpillar | Plants |
|:---------------:|:----:|:----:|:-------:|:----:|:------:|:-----:|:----:|:-----:|:----:|:----:|:-------:|:------:|:----:|:--------:|:--------:|:--------:|
|    **Wolf**     | -    | 10   | 10      | 0    | 10     | 30    | 40   | 70    | 90   | 60   | 70      | 20     | 30   | 80       | 0        | 0   |
|    **Snake**    | 0    | -    | 0       | 0    | 0      | 0     | 0    | 50    | 90   | 0    | 0       | 0      | 0    | 50       | 90       | 0   |
|     **Fox**     | 0    | 20   | -       | 0    | 10     | 0     | 5    | 70    | 90   | 20   | 20      | 5      | 0    | 80       | 0        | 0   |
|    **Bear**     | 20   | 30   | 20      | -    | 30     | 70    | 80   | 80    | 90   | 70   | 70      | 60     | 75   | 80       | 0        | 0   |
|    **Eagle**    | 0    | 50   | 0       | 0    | -      | 0     | 0    | 90    | 90   | 0    | 0       | 0      | 0    | 85       | 20       | 0   |
|    **Horse**    | 0    | 0    | 0       | 0    | 0      | -     | 0    | 0     | 0    | 0    | 0       | 0      | 0    | 0        | 0        | 100 |
|    **Deer**     | 0    | 0    | 0       | 0    | 0      | 0     | -    | 0     | 0    | 0    | 0       | 0      | 0    | 0        | 0        | 100 |
|    **Hare**     | 0    | 0    | 0       | 0    | 0      | 0     | 0    | -     | 0    | 0    | 0       | 0      | 0    | 0        | 0        | 100 |
|   **Hamster**   | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | -    | 0    | 0       | 0      | 0    | 0        | 0        | 100 |
|    **Goat**     | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | -    | 0       | 0      | 0    | 0        | 0        | 100 |
|    **Sheep**    | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | 0    | -       | 0      | 0    | 0        | 0        | 100 |
|  **Kangaroo**   | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | 0    | 0       | -      | 0    | 0        | 0        | 100 |
|     **Cow**     | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | 0    | 0       | 0      | -    | 0        | 0        | 100 |
|    **Duck**     | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | 0    | 0       | 0      | 0    | -        | 50       | 100 |
| **Caterpillar** | 0    | 0    | 0       | 0    | 0      | 0     | 0    | 0     | 0    | 0    | 0       | 0      | 0    | 0        | -        | 100 |

Entities have the main characteristics:

|                 |Weight of entity, kg|Max count on the cell|Max movement count by step|Weight of food for fed-up|Steps with zero-satiety|Satiety decrease by step, %|Probability of reproduction, % |Count of cubs|
|:---------------:|:------------------:|:-------------------:|:------------------------:|:-----------------------:|:---------------------:|:-------------------------:|:-----------------------------:|:-----------:|  
|    **Wolf**     |         50         |         30          |            3             |            8            |          10           |            30             |              30               |      1      |
|    **Snake**    |         2          |         123         |            1             |           0,3           |          15           |            30             |              30               |      1      |
|     **Fox**     |         4          |         50          |            3             |            1            |           8           |            30             |              30               |      1      |
|    **Bear**     |        250         |          7          |            2             |           38            |          15           |            30             |              30               |      1      |
|    **Eagle**    |         6          |         166         |            4             |            1            |           5           |            30             |              30               |      1      |
|    **Horse**    |        300         |         23          |            3             |           45            |           5           |            30             |              30               |      1      |
|    **Deer**     |        170         |         41          |            3             |           26            |           4           |            30             |              30               |      1      |
|    **Hare**     |         3          |         100         |            3             |          0,45           |           7           |            30             |              30               |      1      |
|   **Hamster**   |        0.03        |         100         |            1             |         0.0075          |           3           |            30             |              30               |      1      |
|    **Goat**     |         65         |         107         |            1             |           10            |           5           |            30             |              30               |      1      |
|    **Sheep**    |         45         |         156         |            1             |            7            |           5           |            30             |              30               |      1      |
|  **Kangaroo**   |         47         |         149         |            2             |            7            |           8           |            30             |              30               |      1      |
|     **Cow**     |        350         |         20          |            1             |           53            |           4           |            30             |              30               |      1      |
|    **Duck**     |         1          |         100         |            1             |          0,15           |           4           |            30             |              30               |      1      |
| **Caterpillar** |        0.01        |         200         |            1             |         0.0025          |           1           |            30             |              30               |      1      |
|   **Plants**    |         1          |        1000         |           N/A            |           N/A           |          N/A          |            N/A            |              30               |      1      |

**Technical details**

1. The main concept of the application was implemented using Spring Boot and rest controllers.

There are 6 methods of rest controllers:

| Method path                     | Params | Description                                                                                                                                                        |
|:--------------------------------|:------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| /sessions/init                  |  N/A   | Inits new session and populates the island by entities.                                                                                                            |
| /sessions/{sessionId}/info      |   sessionId     | Present the information about session.                                                                                                                             |
| /sessions/{sessionId}/nextstep  |   sessionId     | Moves the game forward to the step (initiates the moving, repoducing, eating on each field of island) and present the information about changes in the population. |
| /steps/{stepId}/info            |    stepId    | Present the information about step.                                                                                                                                |
| /islands/{islandId}/info        |     islandId   | Present the information about island state.                                                                                                                        |
| /islands/{islandId}/summary     |    islandId    | Present the statistical information about entities on the island.                                                                                                  |

2. Entity characteristics are stored in the resource file "_DefaultCharacteristicsProperties.json_" and are loaded automatically on start the application.
3. Each session can contain many steps. Each step contains only one island state. Each island state contains the fields. Each field contains list of different entities.
4. **For the game play you should:**

_- call the initialization service:_ 

/sessions/init  

_- get the population of the zero step:_

/islands/{islandId}/summary

_- go to the next step:_

/sessions/{sessionId}/nextstep

5. Each method on rest-services has a unit test written using JUnit 5.

Also, you can test the services using curl scripts or Postman. 

Curl script are located in the directory "/scripts/*" (don't forget to change the id's in the files!).

**Contact developer:**

Mike Scherbakov

[scherbakov.mike@gmail.com]()

+7-916-530-3619