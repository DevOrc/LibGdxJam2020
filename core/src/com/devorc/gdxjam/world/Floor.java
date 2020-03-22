package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.Texture;

public enum Floor {

    GRASS("grass"),
    WATER("water"),
    DIRT("dirt"),
    SAND("sand"),
    ROCK("rock");

    /*
    Future Floor IDeas:
    - Lava
    - Nuclear Waste
    - Sand
     */

    private final Texture texture;

    Floor(String name) {
        texture = new Texture("floor_" + name + ".png");
    }

    public static void loadTextures(){}

    public Texture getTexture() {
        return texture;
    }
}
