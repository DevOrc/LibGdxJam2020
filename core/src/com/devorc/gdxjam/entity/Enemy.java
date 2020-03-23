package com.devorc.gdxjam.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Enemy implements Entity {

    protected float x;
    protected float y;

    protected World world;
    private int startHealth;
    private int health;

    public Enemy(int health) {
        this.health = health;
        this.startHealth = health;
    }

    public static void loadTextures(){
        AncientTurret.texture = new Texture("ancient_turret.png");
        AncientTurret.turretTexture = new Texture("ancient_turret_top.png");
    }

    public void damage(int amount){
        health -= amount;
    }

    @Override
    public void render(SpriteBatch batch){
        if(health < startHealth){
            drawHealthBar();
        }
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
    public boolean isDead(){
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
