package com.devorc.gdxjam.world;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.devorc.gdxjam.world.World.WORLD_SIZE;

public class WorldGenerator {

    private static final Random random = new Random();
    private final World world;

    public WorldGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        //ponds
        for(int i = 0; i < 100; i++){
            int x = random.nextInt(WORLD_SIZE);
            int y = random.nextInt(WORLD_SIZE);
            generateFeature(x, y, 5 + (int) (45 * random.nextDouble()), Floor.WATER);
        }

        sandAroundPonds();

        for(int i = 0; i < 180; i++){
            int x = random.nextInt(WORLD_SIZE);
            int y = random.nextInt(WORLD_SIZE);
            generateFeature(x, y, 4, Floor.DIRT);
        }
    }

    private void sandAroundPonds() {
        for(int x = 1; x < WORLD_SIZE - 1; x++) {
            for(int y = 1; y < WORLD_SIZE - 1; y++) {
                Tile tile = world.getTileAt(x, y);

                if(tile.getFloor() == Floor.GRASS){
                    List<Tile> neighbors = getNeighborTiles(x, y);

                    int waterCount = (int) neighbors.stream().filter(t -> t.getFloor() == Floor.WATER).count();

                    if(waterCount > 0 && waterCount < 3){
                        tile.setFloor(Floor.SAND);
                    }
                }
            }
        }
    }

    private List<Tile> getNeighborTiles(int x, int y) {
        List<Tile> neighbors = new ArrayList<>();
        neighbors.add(world.getTileAt(x - 1, y));
        neighbors.add(world.getTileAt(x + 1, y));
        neighbors.add(world.getTileAt(x, y - 1));
        neighbors.add(world.getTileAt(x, y + 1));
        return neighbors;
    }

    private void generateFeature(int startX, int startY, int i, Floor floor) {
        for(int x = startX - 1; x <= startX + 1; x++){
            for(int y = startY - 1; y <= startY + 1; y++) {
                if(random.nextInt(3) != 0)
                    getTileSafe(x, y).ifPresent(tile -> tile.setFloor(floor));
            }
        }

        if(i > 0){
            startX += random.nextBoolean() ? 1 : -1;
            startY += random.nextBoolean() ? 1 : -1;
            generateFeature(startX, startY, i  -  1, floor);
        }
    }

    private Optional<Tile> getTileSafe(int x, int y){
        if(x >= WORLD_SIZE || y >= WORLD_SIZE || x < 0 || y < 0){
            return Optional.empty();
        }

        return Optional.of(world.getTileAt(x, y));
    }

}
