package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.GameRenderer;
import com.devorc.gdxjam.world.World;

public class Mothership extends Enemy {

    private static final float RADIUS = 64;
    private static final float VELOCITY = 250f;
    private static final int SHOOT_RESET_TIME = 180;

    static Texture texture;

    private float renderAngle = 0;
    private final int resetTime;
    private int shootTime;

    public Mothership(int wave) {
        super(25 * wave);

        x = World.WORLD_PIXEL_SIZE / 2f;
        y = World.WORLD_PIXEL_SIZE / 2f;
        resetTime = SHOOT_RESET_TIME - (5 * wave);
        shootTime = resetTime;
    }

    @Override
    public void update() {
        renderAngle += Gdx.graphics.getDeltaTime() * 20;
        float standingDistance = 1600 * 1600;

        if(getDistanceSquaredToRobot() < standingDistance){
            shootTime--;

            if(shootTime < 0){
                Gdx.app.postRunnable(this::shoot);
                shootTime = resetTime;
            }
        }else{
            float angle = getAngleToRobot();
            x += Gdx.graphics.getDeltaTime() * VELOCITY * Math.cos(angle);
            y += Gdx.graphics.getDeltaTime() * VELOCITY * Math.sin(angle);
        }
    }

    private void shoot() {
        double offset = getRadius() + 25;
        double angle = (float) (random.nextFloat() * Math.PI * 2);

        float x = (float) (Math.cos(angle) * offset) + this.x;
        float y = (float) (Math.sin(angle) * offset) + this.y;

        world.addEntity(new Bomber(world, x, y, true));
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

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
    }
}
