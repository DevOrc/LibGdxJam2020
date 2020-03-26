package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.Tile;
import com.devorc.gdxjam.world.World;

import java.util.Random;
import java.util.function.Consumer;

public final class Powerup implements Entity{

    private static final int SPAWN_RANGE = 8000;
    private static final int RADIUS = 8;
    private static final Random random = new Random();

    private final Texture texture;
    private final Consumer<Robot> onPickup;

    private World world;
    private float x;
    private float y;
    private boolean dead;

    public Powerup(Texture texture, World world, Consumer<Robot> onPickup) {
        this.texture = texture;
        this.onPickup = onPickup;

        setWorld(world);
        do {
            selectLocation();
        } while(isInBlock() || !inWorld());
    }

    private void selectLocation() {
        x = (random.nextFloat() - .5f) * SPAWN_RANGE;
        y = (random.nextFloat() - .5f) * SPAWN_RANGE;

        x += world.getRobot().getX();
        y += world.getRobot().getY();
    }

    private boolean inWorld() {
        int offset = 64;
        return x > offset && y > offset && x < World.WORLD_PIXEL_SIZE - offset && y < World.WORLD_PIXEL_SIZE - offset;
    }

    private boolean isInBlock(){
        int tileX = (int) (x / Tile.SIZE);
        int tileY = (int) (y / Tile.SIZE);

        if(tileX < 0 || tileY < 0 || tileX >= World.WORLD_SIZE || tileY >= World.WORLD_SIZE){
            return false;
        }

        Tile tile = world.getTileAt(tileX, tileY);

        return tile.getBlock() != null;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - RADIUS, y - RADIUS);
    }

    @Override
    public void update() {
        if(collided()){
            onPickup.accept(world.getRobot());
            Gdx.app.postRunnable(() -> Powerups.spawnRandom(world));
            dead = true;
        }
    }

    private boolean collided() {
        Robot robot = world.getRobot();
        double radius = RADIUS + robot.getRadius();

        return new Vector2(robot.getX(), robot.getY()).sub(this.x, this.y).len2() < (radius * radius);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
