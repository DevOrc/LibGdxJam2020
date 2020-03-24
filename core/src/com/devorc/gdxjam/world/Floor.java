package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.Texture;

public enum Floor {

    GRASS("grass"),
    DIRT("dirt"),
    SAND("sand"),
    OIL_ROCK("oil_rock"),
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
