package com.devorc.gdxjam.world;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

import static com.devorc.gdxjam.world.World.WORLD_SIZE;

public class WorldGenerator {

    private static final Random random = new Random();
    private final World world;

    public WorldGenerator(World world) {
        this.world = world;
    }

    public void generate() {
        //Ponds
        for(int i = 0; i < 100; i++){
            int x = random.nextInt(WORLD_SIZE);
            int y = random.nextInt(WORLD_SIZE);
            generateFeature(x, y, 5 + (int) (45 * random.nextDouble()), this::setOil);
        }
        fixPatches(Floor.OIL_ROCK, this::setOil);
        surroundFloor(Floor.OIL_ROCK, Floor.SAND);

        //Rock Areas
        for(int i = 0; i < 200; i++){
            int x = random.nextInt(WORLD_SIZE);
            int y = random.nextInt(WORLD_SIZE);
            generateFeature(x, y, (int) (15 + (120 * random.nextDouble())), this::setRock);
        }
        surroundFloor(Floor.ROCK, Floor.DIRT);
        fixPatches(Floor.ROCK, this::setRock);

    }

    private void setOil(Tile tile){
        tile.setFloor(Floor.OIL_ROCK);
        tile.setBlock(Block.OIL);
    }

    private void setRock(Tile tile){
        tile.setFloor(Floor.ROCK);

        if(random.nextInt(3) == 0){
            tile.setBlock(Block.RUBY);
        }else{
            tile.setBlock(Block.ROCK);
        }
    }

    private void fixPatches(Floor type, Consumer<Tile> consumer){
        for(int x = 1; x < WORLD_SIZE - 1; x++) {
            for(int y = 1; y < WORLD_SIZE - 1; y++) {
                Tile tile = world.getTileAt(x, y);

                if(tile.getFloor() != type){
                    List<Tile> neighbors = getNeighborTiles(x, y);

                    int floorCount = (int) neighbors.stream().filter(t -> t.getFloor() == type).count();

                    if(floorCount == 4){
                        consumer.accept(tile);
                    }
                }
            }
        }
    }

    private void surroundFloor(Floor main, Floor border) {
        for(int x = 1; x < WORLD_SIZE - 1; x++) {
            for(int y = 1; y < WORLD_SIZE - 1; y++) {
                Tile tile = world.getTileAt(x, y);

                if(tile.getFloor() == Floor.GRASS){
                    List<Tile> neighbors = getNeighborTiles(x, y);

                    int floorCount = (int) neighbors.stream().filter(t -> t.getFloor() == main).count();

                    if(floorCount > 0 && floorCount < 4){
                        tile.setFloor(border);
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

    private void generateFeature(int startX, int startY, int i, Consumer<Tile> feature) {
        for(int x = startX - 1; x <= startX + 1; x++){
            for(int y = startY - 1; y <= startY + 1; y++) {
                getTileSafe(x, y).ifPresent(feature);
            }
        }

        if(i > 0){
            startX += random.nextBoolean() ? 1 : -1;
            startY += random.nextBoolean() ? 1 : -1;
            generateFeature(startX, startY, i  -  1, feature);
        }
    }

    private Optional<Tile> getTileSafe(int x, int y){
        if(x >= WORLD_SIZE || y >= WORLD_SIZE || x < 0 || y < 0){
            return Optional.empty();
        }

        return Optional.of(world.getTileAt(x, y));
    }

}
