package com.devorc.gdxjam.world;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.Item;
import com.devorc.gdxjam.Robot;
import com.devorc.gdxjam.entity.AncientTurret;
import com.devorc.gdxjam.entity.Enemy;
import com.devorc.gdxjam.entity.Entity;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class World {

    public static final int WORLD_SIZE = 800;
    public static final int WORLD_PIXEL_SIZE = WORLD_SIZE * Tile.SIZE;

    private final Robot robot;
    private final Game game;

    private final Tile[][] tiles = new Tile[WORLD_SIZE][WORLD_SIZE];
    private final EnumMap<Item, Integer> inventory = new EnumMap<>(Item.class);

    private final List<Entity> entities = new LinkedList<>();
    private final List<Enemy> enemies = new LinkedList<>();

    public World(Game game) {
        this.game = game;

        robot = new Robot(game);
        createTiles();

        new WorldGenerator(this).generate();

        for(Item item: Item.values()){
            inventory.put(item, 0);
        }

        addEntity(new AncientTurret());
    }

    public void addEntity(Entity entity){
        entity.setWorld(this);
        entities.add(entity);

        if(entity instanceof Enemy){
            enemies.add((Enemy) entity);
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
        entities.forEach(Entity::update);
        entities.removeIf(Entity::isDead);
        enemies.removeIf(Enemy::isDead);

        robot.update();

        if(robot.isDead()){
            System.out.println("You lost");
            Gdx.app.exit();
        }
    }

    public void render(SpriteBatch batch) {
        int playerX = (int) (robot.getX() / Tile.SIZE);
        int playerY = (int) (robot.getY() / Tile.SIZE);
        int renderArea = Gdx.app.getType() == Application.ApplicationType.WebGL ? 15: 50;

        for(int x = playerX - renderArea; x < playerX + renderArea; x++) {
            for(int y = playerY - renderArea; y < playerY + renderArea; y++) {
                if(x >= WORLD_SIZE || y >= WORLD_SIZE || x < 0 || y < 0){
                    continue;
                }

                tiles[x][y].render(batch);
            }
        }

        entities.forEach(e -> e.render(batch));
        robot.render(batch);
    }

    public void changeItemAmount(Item item, int delta){
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
        return enemies;
    }

    public EnumMap<Item, Integer> getInventory() {
        return inventory;
    }
}
