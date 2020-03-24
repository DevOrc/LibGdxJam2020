package com.devorc.gdxjam;

import com.badlogic.gdx.graphics.Texture;

public enum Item {

    ROCK("rock"), RUBY("ruby"), PCB("pcb");

    private final Texture texture;

    Item(String path) {
        this.texture = new Texture("item_" + path + ".png");
    }

    public static void loadTextures(){}

    public Texture getTexture() {
        return texture;
    }
}
