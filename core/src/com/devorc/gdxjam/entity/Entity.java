package com.devorc.gdxjam.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.world.World;

public interface Entity {

    void render(SpriteBatch batch);

    void update();

    boolean isDead();

    void setWorld(World world);
}
