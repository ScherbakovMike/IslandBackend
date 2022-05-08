package com.example.islandbackend.models.areas;

import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Island")
public class Island {

    @Value("island.width.default")
    private static int defaultWidth;
    @Value("island.height.default")
    private static int defaultHeight;
    private int width;
    private int height;

    private final Field[][] fields = new Field[height][width];

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Island newInstance() {
        Island result = new Island(defaultWidth, defaultHeight);
        for (int i = 0; i < defaultHeight; i++) {
            for (int j = 0; j < defaultWidth; j++) {
                result.fields[i][j] = Field.newInstance();
            }
        }
        return result;
    }

    protected static Island populate(Island island) {
        var kindsOfDieable = CharacteristicsHelpers.kindsOfDieable();

        return island;
    }


}
