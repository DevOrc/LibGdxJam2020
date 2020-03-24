package com.devorc.gdxjam.entity;

import com.badlogic.gdx.graphics.Texture;

public class Mothership extends Enemy {

    private static final int RADIUS = 64;

    static Texture texture;

    public Mothership() {
        super(75);
    }

    @Override
    public void update() {

    }

    @Override
    public float getRadius() {
        return RADIUS;
    }
}
