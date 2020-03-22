package com.devorc.gdxjam.world;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {

    private static final Random random = new Random();
    private final World world;

    private final double[][] values = new double[World.WORLD_SIZE][World.WORLD_SIZE];

    public WorldGenerator(World world) {
        this.world = world;

        for(int x = 0; x < World.WORLD_SIZE; x++) {
            for(int y = 0; y <  World.WORLD_SIZE; y++) {
                values[x][y] = random.nextDouble();
            }
        }
    }

    public void generate() {
        smoothWorld();

        for(int x = 0; x < World.WORLD_SIZE; x++) {
            for(int y = 0; y <  World.WORLD_SIZE; y++) {
                mapValueToFloor(world.getTileAt(x, y), values[x][y]);
            }
        }
    }

    private void smoothWorld() {
        for(int x = 1; x < World.WORLD_SIZE - 1; x++) {
            for(int y = 1; y <  World.WORLD_SIZE - 1; y++) {
                List<Double> tiles = new ArrayList<>();
                tiles.add(values[x][y]);
                tiles.add(values[x][y]);
                tiles.add(values[x][y]);
                tiles.add(values[x + 1][y]);
                tiles.add(values[x - 1][y]);
                tiles.add(values[x][y + 1]);
                tiles.add(values[x][y - 1]);
                tiles.add(values[x - 1][y + 1]);
                tiles.add(values[x - 1][y - 1]);
                tiles.add(values[x + 1][y + 1]);
                tiles.add(values[x + 1][y - 1]);

                values[x][y] = average(tiles);
            }
        }
    }

    private double average(List<Double> values) {
        double sum = 0;

        for(double val : values){
            sum += Math.sqrt(val);
        }

        return sum / values.size();
    }

    private void mapValueToFloor(Tile tile, double v) {
        if(v < .15){
            tile.setFloor(Floor.DIRT);
        }else if(v < .8){
            tile.setFloor(Floor.GRASS);
        }else if(v < .85){
            tile.setFloor(Floor.SAND);
        }else{
            tile.setFloor(Floor.WATER);
        }
    }
}
