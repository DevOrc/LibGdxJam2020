package com.devorc.gdxjam.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.GameRenderer;
import com.devorc.gdxjam.Item;
import com.devorc.gdxjam.entity.Bullet;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Tile;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Robot {

    private static final int SIZE = 64;
    private static Texture onTexture;
    private static Texture offTexture;

    private static float MAX_VELOCITY = 950f;

    private final RobotStats stats = new RobotStats();
    private final Game game;

    private float x = World.WORLD_PIXEL_SIZE / 2f;
    private float y = World.WORLD_PIXEL_SIZE / 2f;
    private float angle = (float) (Math.PI / 2);

    private float velocity;
    private float angularVelocity;

    private boolean accelerating;

    private boolean runLaser;
    private Tile miningLocation;
    private double laserTime;

    private int maxHealth = 125;
    private int health = maxHealth;

    private float maxOil = 500;
    private float oilLevel = maxOil;

    private int shooterTime = 15;
    private int shooterTick = 0;

    public Robot(Game game) {
        this.game = game;
    }

    public static void loadTexture(){
        onTexture = new Texture("robot_on.png");
        offTexture = new Texture("robot_off.png");

        MiningParticle.loadParticle();
    }

    public void damage(int amount) {
        health -= amount;
    }

    public void update() {
        updateControls();
        updatePositionVelocity();

        updateShooter();
        runLaser();

        updateOil();
        updateHealth();
        trimRobotPositionToWorld();
    }

    private void updateHealth() {
        if(health < maxHealth){
            health++;
            oilLevel -= 4;
        }
    }

    private void updateOil() {
        if(accelerating){
            oilLevel -=  Gdx.graphics.getDeltaTime();
        }

        if(runLaser){
            oilLevel -= .25 * Gdx.graphics.getDeltaTime();
        }

        oilLevel -= .1 * Gdx.graphics.getDeltaTime();

        if(oilLevel < 0){
            oilLevel = 0;
        }else if(oilLevel > maxOil){
            oilLevel = maxOil;
        }
    }

    private void updateShooter() {
        shooterTick++;

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shooterTick >= shooterTime){
            fireBullet();
            shooterTick = 0;
        }

        if(shooterTick > shooterTime){
            shooterTick = shooterTime;
        }
    }

    private void fireBullet() {
        float offset = getRadius() + 20;
        float x = (float) (this.x + (offset * Math.cos(angle)));
        float y = (float) (this.y + (offset * Math.sin(angle)));
        Bullet bullet = new Bullet(x, y, angle, 1500 + velocity, 5);

        game.getWorld().addEntity(bullet);
    }

    private void runLaser() {
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            runLaser = true;
            laserTime -= 30 * Gdx.graphics.getDeltaTime();

            if(laserTime < 0){
                mineBlock();
            }
        }else{
            laserTime = stats.laserLevel.getValue().doubleValue();
            runLaser = false;
        }
    }

    private void mineBlock() {
        if(miningLocation.getBlock() == null)
            return;


        if(miningLocation.getBlock() == Block.OIL){
            oilLevel += 150;
            miningLocation.setBlock(null);
            return;
        }

        Item item = miningLocation.getBlock().getItem();

        if(item != null){
            game.getWorld().changeItemAmount(item, 1);
        }

        miningLocation.setBlock(null);
        laserTime = stats.laserLevel.getValue().doubleValue();
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
            velocity -= 1100 * Gdx.graphics.getDeltaTime();
            accelerating = false;
        }else{
            accelerating = false;
            velocity -= 2 * velocity * Gdx.graphics.getDeltaTime();
        }

        if(velocity < 0){
            velocity = 0;
        }else if(velocity > MAX_VELOCITY){
            velocity = MAX_VELOCITY;
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
        }else{
            MiningParticle.reset();
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

        MiningParticle.update(miningLocation.x, miningLocation.y);
        MiningParticle.draw(batch);

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
            laserTime = stats.laserLevel.getValue().doubleValue();
        }

        miningLocation = miningTile;
    }

    public boolean isDead(){
        return health <= 0 || oilLevel <= 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getRadius() {
        return onTexture.getWidth() / 2f;
    }

    public float getOilLevel() {
        return oilLevel;
    }

    public float getMaxOil() {
        return maxOil;
    }

    public RobotStats getStats() {
        return stats;
    }
}
