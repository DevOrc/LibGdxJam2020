package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class ColorDrawable extends BaseDrawable {

    public static Texture dot;

    static {
        if(Gdx.graphics != null) {
            Pixmap map = new Pixmap(1, 1, Pixmap.Format.RGB565);

            map.setColor(Color.WHITE);
            map.drawPixel(0, 0);

            dot = new Texture(map);
        }
    }

    private final Color color;
    private final Color border;

    public ColorDrawable(Color color, Color border) {
        super();
        this.color = color;
        this.border = border;
    }

    public static void init(){}

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.setColor(border);
        batch.draw(dot, x, y, width, height);
        batch.setColor(color);
        batch.draw(dot, x + 2, y + 2, width - 4, height - 4);
        batch.setColor(Color.WHITE);
    }
}
