package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;

public class Font {

    private static BitmapFont font;
    private static BitmapFont titleFont;
    private static BitmapFont buttonFont;
    private static BitmapFont smallFont;

    public static void loadFont(){
        font = new BitmapFont(Gdx.files.internal("ui.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("title.fnt"));
        buttonFont = new BitmapFont(Gdx.files.internal("button.fnt"));
        smallFont = VisUI.getSkin().getFont("small-font");
    }

    public static void setData(Color color, float size){
        setData(font, color, size);
    }

    public static void setData(BitmapFont font, Color color, float size){
        font.getData().setScale(size);
        font.setColor(color);
    }

    public static void render(String text, int x, int y, SpriteBatch batch){
        font.draw(batch, text, x, y);
    }

    public static void drawCenter(float x, float y, float width, String text, SpriteBatch batch) {
        drawCenter(font, x, y, width, text, batch);
    }

    public static void drawCenter(BitmapFont font, float x, float y, float width, String text, SpriteBatch batch) {
        font.draw(batch, text, x, y, width, Align.center, false);
    }

    public static BitmapFont getFont() {
        return font;
    }

    public static BitmapFont getTitleFont() {
        return titleFont;
    }

    public static BitmapFont getButtonFont() {
        return buttonFont;
    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }
}
