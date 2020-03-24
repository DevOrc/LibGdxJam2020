package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Tile;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Random;

public abstract class Enemy implements Entity {

    protected static final Random random = new Random();

    protected float x;
    protected float y;

    protected World world;
    private int startHealth;
    private int health;

    public Enemy(int health) {
        this.health = health;
        this.startHealth = health;
    }

    public static void loadTextures() {
        AncientTurret.texture = new Texture("ancient_turret.png");
        AncientTurret.turretTexture = new Texture("ancient_turret_top.png");
        Bomber.texture = new Texture("bomber.png");

        Explosion.loadEffect();
    }

    public void damage(int amount) {
        health -= amount;

        if(isDead()){
            onDeath();
        }
    }

    protected void onDeath() {
        Gdx.app.postRunnable(() -> world.addEntity(new Explosion(x, y)));
    }

    void die() {
        onDeath();
        health = -1;
    }

    protected float getAngleToRobot() {
        Robot robot = world.getRobot();
        Vector2 turretPos = new Vector2(x, y);
        Vector2 robotPos = new Vector2(robot.getX(), robot.getY());

        return robotPos.sub(turretPos).angleRad();
    }

    @Override
    public void render(SpriteBatch batch) {
        if(health < startHealth) {
            drawHealthBar();
        }
    }

    protected boolean isInBlock(boolean countOil) {
        int tileX = (int) Math.floor(x / Tile.SIZE);
        int tileY = (int) Math.floor(y / Tile.SIZE);

        if(tileX < 0 || tileY < 0 || tileX >= World.WORLD_SIZE || tileY >= World.WORLD_SIZE) {
            return false;
        }

        Block block = world.getTileAt(tileX, tileY).getBlock();

        if(countOil)
            return block != null;

        return block != null && block != Block.OIL;
    }

    private void drawHealthBar() {
        ShapeDrawer drawer = world.getGame().getRenderer().getShapeDrawer();
        float x = getX() - getRadius();
        float y = getY() + getRadius() + 12;
        float width = getRadius() * 2;
        float height = 5;
        float border = 2f;

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x - border, y - border, width + (border * 2), height + (border * 2));
        drawer.setColor(Color.RED);
        drawer.filledRectangle(x, y, width * health / startHealth, height);
    }

    @Override
    public abstract void update();

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract float getRadius();
}
