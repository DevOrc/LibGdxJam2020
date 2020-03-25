package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;
import java.util.Random;

public class Tile {

    private static final Random random = new Random();

    public static final int SIZE = 32;

    private World world;
    private final int x;
    private final int y;

    private Floor floor;
    private Block block;

    Tile(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;

        if(random.nextInt(3) == 0){
            setFloor(Floor.DIRT_2);
        }else if(random.nextInt(4) == 0){
            setFloor(Floor.DIRT_3);
        }else{
            setFloor(Floor.DIRT);
        }
    }

    public void render(SpriteBatch batch) {
        int pixelX = x * SIZE;
        int pixelY = y * SIZE;

        batch.draw(floor.getTexture(), pixelX, pixelY);

        if(block != null){
            batch.draw(block.getTexture(), pixelX, pixelY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return x == tile.x &&
                y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Floor getFloor() {
        return floor;
    }
}
