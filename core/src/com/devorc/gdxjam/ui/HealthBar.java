package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.robot.Robot;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class HealthBar {

    private static final float WIDTH = 250f;
    private static final float HEIGHT = 25f;
    private static final float BORDER = 3f;

    public static void render(SpriteBatch batch, ShapeDrawer drawer, Robot robot){
        float x = (Gdx.graphics.getWidth() / 2f) - (WIDTH / 2f);
        float y = 25;
        float innerBar = WIDTH * robot.getHealth() / robot.getMaxHealth();

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x - BORDER, y - BORDER, WIDTH + (BORDER * 2), HEIGHT + (BORDER * 2));
        drawer.setColor(Color.RED);
        drawer.filledRectangle(x, y, innerBar, HEIGHT);

        drawText(x, y, batch, robot);
    }

    private static void drawText(float x, float y, SpriteBatch batch, Robot robot) {
        String text = robot.getHealth() + " / " + robot.getMaxHealth() + " HP";
        y += HEIGHT - 5;

        Font.setData(Color.BLACK, .8f);
        Font.drawCenter(x, y, WIDTH, text, batch);
    }
}
