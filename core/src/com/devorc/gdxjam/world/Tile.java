package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Tile {

    private static final Random random = new Random();

    public static final int SIZE = 32;

    private World world;
    private final int x;
    private final int y;

    private Floor floor;

    Tile(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;

        setFloor(Floor.GRASS);
    }

    public void render(SpriteBatch batch) {
        int pixelX = x * SIZE;
        int pixelY = y * SIZE;

        batch.draw(floor.getTexture(), pixelX, pixelY);
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Floor getFloor() {
        return floor;
    }
}
