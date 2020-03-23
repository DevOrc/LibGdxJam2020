package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class Font {

    private static BitmapFont font;

    public static void loadFont(){
        font = new BitmapFont(Gdx.files.internal("ui.fnt"));
    }

    public static void setData(Color color, float size){
        font.getData().setScale(size);
        font.setColor(color);
    }

    public static void render(String text, int x, int y, SpriteBatch batch){
        font.draw(batch, text, x, y);
    }

    public static void drawCenter(float x, float y, float width, String text, SpriteBatch batch) {
        font.draw(batch, text, x, y, width, Align.center, false);
    }
}
