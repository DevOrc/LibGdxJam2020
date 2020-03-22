package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.Texture;

public enum Block {

    ROCK("rock");

    private final Texture texture;

    Block(String path) {
        this.texture = new Texture("block_" + path + ".png");
    }

    public static void loadTextures(){}

    public Texture getTexture() {
        return texture;
    }
}
