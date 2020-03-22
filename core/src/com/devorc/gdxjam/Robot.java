package com.devorc.gdxjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.devorc.gdxjam.world.Tile;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Robot {

    private static final int SIZE = 64;
    private static Texture onTexture;
    private static Texture offTexture;

    private final Game game;

    private float x = World.WORLD_PIXEL_SIZE / 2f;
    private float y = World.WORLD_PIXEL_SIZE / 2f;
    private float angle = (float) (Math.PI / 2);

    private float velocity;
    private float angularVelocity;

    private boolean accelerating;

    private boolean runLaser;
    private Tile miningLocation;
    private double laserReset = 100;
    private double laserTime = laserReset;

    public Robot(Game game) {
        this.game = game;
    }


    public static void loadTexture(){
        onTexture = new Texture("robot_on.png");
        offTexture = new Texture("robot_off.png");
    }

    public void update() {
        updateControls();
        updatePositionVelocity();
        runLaser();

        trimRobotPositionToWorld();
    }

    private void runLaser() {
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            runLaser = true;
            laserTime -= 30 * Gdx.graphics.getDeltaTime();

            if(laserTime < 0){
                miningLocation.setBlock(null);
                laserTime = laserReset;
            }
        }else{
            laserTime = laserReset;
            runLaser = false;
        }
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
        int x = (int) (this.x - (SIZE / 2));
        int y = (int) (this.y - (SIZE / 2));

        GameRenderer.drawRotated(batch, texture, x, y, (float) (angle * 180f / Math.PI) - 90);

        if(runLaser){
            drawLaser(batch);
        }
    }

    private void drawLaser(SpriteBatch batch) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        Vector3 worldCoords = game.getRenderer().unProject(mouseX, mouseY);
        ShapeDrawer sr = game.getRenderer().getShapeDrawer();
        Vector2 miningLocation = new Vector2(worldCoords.x - x, worldCoords.y - y);
        miningLocation.clamp(0 , 250).add(x, y);
        updateLaserTile(miningLocation);

        sr.setColor(Color.YELLOW);
        sr.setDefaultLineWidth(5);
        sr.line(x, y, miningLocation.x, miningLocation.y);
        sr.setColor(Color.RED);
        sr.filledCircle(miningLocation.x, miningLocation.y, 4);
    }

    private void updateLaserTile(Vector2 pos) {
        int tileX = (int) Math.floor(pos.x / Tile.SIZE);
        int tileY = (int) Math.floor(pos.y / Tile.SIZE);

        World world = game.getWorld();
        Tile miningTile = world.getTileAt(tileX, tileY);

        if(!miningTile.equals(miningLocation)){
            laserTime = laserReset;
        }

        miningLocation = miningTile;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
