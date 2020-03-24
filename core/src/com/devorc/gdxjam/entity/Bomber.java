package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.GameRenderer;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.World;

public class Bomber extends Enemy {

    private static final int RADIUS = 12;
    private static final int DAMAGE = 25;
    private static final float VELOCITY = 200f;

    static Texture texture;

    private float renderAngle = 0;

    public Bomber() {
        super(10);

        x = World.WORLD_PIXEL_SIZE / 2f;
        y = World.WORLD_PIXEL_SIZE / 2f - 200;
    }

    @Override
    public void update() {
        renderAngle += Gdx.graphics.getDeltaTime() * 40;
        float angle = getAngleToRobot();

        x += VELOCITY * Gdx.graphics.getDeltaTime() * Math.cos(angle);
        y += VELOCITY * Gdx.graphics.getDeltaTime() * Math.sin(angle);

        if(collided()){
            world.getRobot().damage(DAMAGE);
            die();
        }

        if(isInBlock(false)){
            die();
        }
    }


    private boolean collided() {
        Robot robot = world.getRobot();
        return new Vector2(robot.getX(), robot.getY()).sub(this.x, this.y).len2() < (RADIUS * RADIUS);
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
