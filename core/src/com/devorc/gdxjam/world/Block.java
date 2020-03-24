package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.Texture;
import com.devorc.gdxjam.Item;

public enum Block {

    ROCK("rock", Item.ROCK),
    RUBY("ruby", Item.RUBY),
    OIL("oil", null);

    private final Texture texture;
    private final Item item;

    Block(String path, Item item) {
        this.texture = new Texture("block_" + path + ".png");
        this.item = item;
    }

    public static void loadTextures(){}

    public Item getItem() {
        return item;
    }

    public Texture getTexture() {
        return texture;
    }
}
