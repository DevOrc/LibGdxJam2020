package com.devorc.gdxjam.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.Item;
import com.devorc.gdxjam.entity.Enemy;
import com.devorc.gdxjam.entity.Entity;
import com.devorc.gdxjam.entity.Explosion;
import com.devorc.gdxjam.entity.Powerups;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.ui.UIScenes;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class World {

    public static final int WORLD_SIZE = 800;
    public static final int WORLD_PIXEL_SIZE = WORLD_SIZE * Tile.SIZE;

    private final Robot robot;
    private final Game game;
    private final EnemyManager enemyManager;

    private final Tile[][] tiles = new Tile[WORLD_SIZE][WORLD_SIZE];
    private final EnumMap<Item, Integer> inventory = new EnumMap<>(Item.class);

    private final List<Entity> entities = new LinkedList<>();
    private long gameTime = System.currentTimeMillis();

    private boolean gameOver = false;

    public World(Game game) {
        this(game, new Robot(game));
    }

    public World(Game game, Robot robot) {
        this.robot = robot;
        this.game = game;
        this.enemyManager = new EnemyManager(this);

        createTiles();
        new WorldGenerator(this).generate();

        for(int i = 0; i < 5; i++){
            Powerups.spawnRandom(this);
        }

        for(Item item : Item.values()) {
            inventory.put(item, 0);
        }
    }

    public void resetTimes(){
        gameTime = System.currentTimeMillis();
        enemyManager.setWave(1);
    }

    public void addEntity(Entity entity) {
        entity.setWorld(this);
        entities.add(entity);

        if(entity instanceof Enemy) {
            enemyManager.add((Enemy) entity);
        }
    }

    private void createTiles() {
        for(int x = 0; x < WORLD_SIZE; x++) {
            for(int y = 0; y < WORLD_SIZE; y++) {
                tiles[x][y] = new Tile(this, x, y);
            }
        }
    }

    public void update() {
        if(gameOver)
            return;

        entities.forEach(Entity::update);
        entities.removeIf(Entity::isDead);
        enemyManager.update();

        robot.update();

        if(robot.isDead()) {
            endGame();
        }
    }

    private void endGame() {
        gameOver = true;
        game.getUI().setScene(UIScenes.GAME_OVER);
        gameTime = System.currentTimeMillis() - gameTime;

        addEntity(new Explosion(robot.getX(), robot.getY(), 8f));
    }

    public void render(SpriteBatch batch) {
        int playerX = (int) (robot.getX() / Tile.SIZE);
        int playerY = (int) (robot.getY() / Tile.SIZE);
        int renderWidth = getRenderWidth();
        int renderHeight = getRenderHeight();

        for(int x = playerX - renderWidth; x < playerX + renderWidth; x++) {
            for(int y = playerY - renderHeight; y < playerY + renderHeight; y++) {
                if(x >= WORLD_SIZE || y >= WORLD_SIZE || x < 0 || y < 0) {
                    continue;
                }

                tiles[x][y].render(batch);
            }
        }

        for(Entity e : entities) {
            if(!(e instanceof Explosion))
                e.render(batch);
        }
        if(!gameOver)
            robot.render(batch);
        for(Entity e : entities) {
            if(e instanceof Explosion)
                e.render(batch);
        }
    }

    private int getRenderWidth() {
        //Add four to make sure the background never shows
        return (Math.floorDiv(Gdx.graphics.getWidth() + 200, Tile.SIZE) / 2) + 4;
    }

    private int getRenderHeight() {
        //Add four to make sure the background never shows
        return (Math.floorDiv(Gdx.graphics.getHeight(), Tile.SIZE) / 2) + 4;
    }

    public void changeItemAmount(Item item, int delta) {
        int val = inventory.getOrDefault(item, 0);
        val += delta;

        inventory.put(item, val);
    }

    public Tile getTileAt(int x, int y) {
        return tiles[x][y];
    }

    public Robot getRobot() {
        return robot;
    }

    public Game getGame() {
        return game;
    }

    public List<Enemy> getEnemies() {
        return enemyManager.getEnemies();
    }

    public EnumMap<Item, Integer> getInventory() {
        return inventory;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public long getGameTime() {
        return gameTime;
    }
}
