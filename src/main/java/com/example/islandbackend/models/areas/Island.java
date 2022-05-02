package com.example.islandbackend.models.areas;

public class Island {
    private int width = 0;
    private int height = 0;
    private final Field[][] fields = new Field[height][width];

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
