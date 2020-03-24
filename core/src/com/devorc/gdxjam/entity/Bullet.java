package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Tile;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Bullet implements Entity {

    private World world;
    private boolean dead = false;

    private final float velocity;
    private final int damage;
    private final int radius;
    private boolean ignoreRobot = false;

    private float x;
    private float y;
    private float angle;

    public Bullet(float x, float y, float angle, float velocity, int damage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.damage = damage;
        this.radius = 5 + (damage / 4);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(isInBlock()){
            return;
        }

        ShapeDrawer drawer = world.getGame().getRenderer().getShapeDrawer();
        drawer.setColor(Color.FIREBRICK);
        drawer.filledCircle(x, y, radius);
    }

    @Override
    public void update() {
        if(isOutsideWorld() || isInBlock()){
            dead = true;
        }

        checkCollision();

        x += velocity * Gdx.graphics.getDeltaTime() * Math.cos(angle);
        y += velocity * Gdx.graphics.getDeltaTime() * Math.sin(angle);
    }

    private void checkCollision() {
        for(Enemy enemy: world.getEnemies()){
            if(enemy.isDead())
                continue;

            if(collided(enemy.getX(), enemy.getY(), enemy.getRadius())){
                dead = true;
                enemy.damage(damage);
                return;
            }
        }

        if(!ignoreRobot)
            checkRobotCollision();
    }

    private void checkRobotCollision() {
        Robot robot = world.getRobot();

        if(collided(robot.getX(), robot.getY(), robot.getRadius())){
            robot.damage(damage);
            dead = true;
        }
    }

    private boolean collided(float x, float y, float radius) {
        radius += this.radius;
        return new Vector2(x, y).sub(this.x, this.y).len2() < (radius * radius);
    }

    private boolean isInBlock() {
        int tileX = (int) Math.floor(x / Tile.SIZE);
        int tileY = (int) Math.floor(y / Tile.SIZE);

        if(tileX < 0 || tileY < 0 || tileX >= World.WORLD_SIZE || tileY >= World.WORLD_SIZE){
            return false;
        }

        Block block = world.getTileAt(tileX, tileY).getBlock();

        return block != null && block != Block.OIL;
    }

    private boolean isOutsideWorld() {
        return x < 0 || y < 0 || x > World.WORLD_PIXEL_SIZE || y > World.WORLD_PIXEL_SIZE;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    public void setIgnoreRobot(boolean ignoreRobot) {
        this.ignoreRobot = ignoreRobot;
    }
}
