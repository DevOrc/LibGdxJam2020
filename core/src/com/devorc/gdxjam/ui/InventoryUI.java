package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.Item;
import com.devorc.gdxjam.world.World;

import java.util.Map;

public class InventoryUI {

    public static void render(SpriteBatch batch, World world){
        Map<Item, Integer> inventory = world.getInventory();
        Item[] items = Item.values();
        int x = 10;
        int y = Gdx.graphics.getHeight() - 50;

        Font.setData(Color.WHITE, 1f);
        for(int i = 0; i < items.length; i++){
            String text = items[i].name() + ": " + inventory.getOrDefault(items[i], 0);

            batch.draw(items[i].getTexture(), x, y);
            Font.render(text, x + 48, y + 26, batch);

            y -= 40;
        }
    }
}
