package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.Robot;

public class World {

    public static final int WORLD_SIZE = 800;
    public static final int WORLD_PIXEL_SIZE = WORLD_SIZE * Tile.SIZE;

    private final Robot robot;
    private final Game game;

    private final Tile[][] tiles = new Tile[WORLD_SIZE][WORLD_SIZE];

    public World(Game game) {
        this.game = game;

        robot = new Robot();
        createTiles();

        new WorldGenerator(this).generate();
    }

    private void createTiles() {
        for(int x = 0; x < WORLD_SIZE; x++) {
            for(int y = 0; y < WORLD_SIZE; y++) {
                tiles[x][y] = new Tile(this, x, y);
            }
        }
    }

    public void update() {
        robot.update();
    }

    public void render(SpriteBatch batch) {
        int playerX = (int) (robot.getX() / Tile.SIZE);
        int playerY = (int) (robot.getY() / Tile.SIZE);

        for(int x = playerX - 50; x < playerX + 50; x++) {
            for(int y = playerY - 50; y < playerY + 50; y++) {
                if(x >= WORLD_SIZE || y >= WORLD_SIZE || x < 0 || y < 0){
                    continue;
                }

                tiles[x][y].render(batch);
            }
        }

        robot.render(batch);
    }

    public Tile getTileAt(int x, int y) {
        return tiles[x][y];
    }

    public Robot getRobot() {
        return robot;
    }
}
