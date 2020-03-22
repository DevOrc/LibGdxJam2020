package com.devorc.gdxjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.world.World;

public class Robot {

    private static final int SIZE = 64;
    private static Texture onTexture;
    private static Texture offTexture;

    private float x = World.WORLD_PIXEL_SIZE / 2f;
    private float y = World.WORLD_PIXEL_SIZE / 2f;
    private float angle = (float) (Math.PI / 2);

    private float velocity;
    private float angularVelocity;

    private boolean accelerating;

    public static void loadTexture(){
        onTexture = new Texture("robot_on.png");
        offTexture = new Texture("robot_off.png");
    }

    public void update() {
        updateControls();
        updatePositionVelocity();

        trimRobotPositionToWorld();
    }

    private void trimRobotPositionToWorld() {
        if(x + (SIZE / 2f) < 0) {
            x = (SIZE / 2f);
            velocity = 0;
        }
        if(y < (SIZE / 2f)){
            y = (SIZE / 2f);
            velocity = 0;
        }
        if(x > World.WORLD_PIXEL_SIZE - (SIZE / 2f)){
            x = World.WORLD_PIXEL_SIZE - SIZE;
            velocity = 0;
        }
        if(y > World.WORLD_PIXEL_SIZE - (SIZE / 2f)){
            y = World.WORLD_PIXEL_SIZE - SIZE;
            velocity = 0;
        }
    }

    private void updatePositionVelocity() {
        x += velocity * Gdx.graphics.getDeltaTime() * Math.cos(angle);
        y += velocity * Gdx.graphics.getDeltaTime() * Math.sin(angle);
        angle += -angularVelocity * Gdx.graphics.getDeltaTime();

        if(Math.abs(angularVelocity) > 1.7){
            angularVelocity = 1.7f * Math.signum(angularVelocity);
        }
    }

    private void updateControls() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity += 250 * Gdx.graphics.getDeltaTime();
            accelerating = true;
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velocity -= 350 * Gdx.graphics.getDeltaTime();
            accelerating = false;
        }else{
            accelerating = false;
            velocity -= 250 * Gdx.graphics.getDeltaTime();
        }

        if(velocity < 0){
            velocity = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            angularVelocity += 3 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            angularVelocity -= 3 * Gdx.graphics.getDeltaTime();
        }else{
            angularVelocity -= 5 * angularVelocity * Gdx.graphics.getDeltaTime();
        }
    }

    public void render(SpriteBatch batch) {
        Texture texture = accelerating ? onTexture : offTexture;
        int x = (int) (this.x + (SIZE / 2));
        int y = (int) (this.y + (SIZE / 2));

        GameRenderer.drawRotated(batch, texture, x, y, (float) (angle * 180f / Math.PI) - 90);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
