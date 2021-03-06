package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.GameRenderer;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.World;

public class Bomber extends Enemy {

    private static final int HEALTH = 10;
    private static final int RADIUS = 12;
    private static final int DAMAGE = 25;
    private static final float VELOCITY = 600;

    static Texture texture;

    private float renderAngle = 0;
    private boolean ignoreBlocks;

    public Bomber(World world) {
        super(HEALTH);
        setWorld(world);

        do{
            selectStartingPosition(1200);
        }while(isInBlock(true) || !inWorld());
    }

    public Bomber(World world, float x, float y, boolean ignoreBlocks) {
        super(10);
        setWorld(world);

        this.x = x;
        this.y = y;
        this.ignoreBlocks = ignoreBlocks;
    }

    @Override
    public void update() {
        renderAngle += Gdx.graphics.getDeltaTime() * 120;
        float angle = getAngleToRobot();

        x += VELOCITY * Gdx.graphics.getDeltaTime() * Math.cos(angle);
        y += VELOCITY * Gdx.graphics.getDeltaTime() * Math.sin(angle);

        if(collided()){
            world.getRobot().damage(DAMAGE);
            die();
        }

    if(isInBlock(false) && !ignoreBlocks){
            die();
        }
    }


    private boolean collided() {
        Robot robot = world.getRobot();
        double radius = RADIUS + robot.getRadius();
        return new Vector2(robot.getX(), robot.getY()).sub(this.x, this.y).len2() < (radius * radius);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        float x = this.x - (RADIUS / 2f);
        float y = this.y - (RADIUS / 2f);

        GameRenderer.drawRotated(batch, texture, x, y, renderAngle);
    }

    @Override
    public float getRadius() {
        return RADIUS;
    }
}
