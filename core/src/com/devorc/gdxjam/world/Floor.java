package com.devorc.gdxjam.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Floor {

    DIRT(0,0),
    DIRT_2(2, 0),
    DIRT_3(1, 1),
    SAND(3, 0),
    OIL_ROCK(1, 0),
    ROCK(0, 1);

    /*
    Future Floor IDeas:
    - Lava
    - Nuclear Waste
    - Sand
     */

    private final TextureRegion texture;
    private final int spriteX;
    private final int spriteY;

    Floor(int x, int y) {
        texture = new TextureRegion();

        this.spriteX = x;
        this.spriteY = y;
    }

    public static void loadTextures(){
        Texture texture = new Texture("floors.png");
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        for(Floor floor: values()){
            floor.texture.setTexture(texture);
            floor.texture.setRegion(floor.spriteX * 34  + 1, floor.spriteY * 34 + 1, 32, 32);
        }
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
