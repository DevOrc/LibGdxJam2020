package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.EnemyManager;
import com.devorc.gdxjam.world.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class StatsBar {

    private static final float WIDTH = 250f;
    private static final float HEIGHT = 40f;
    private static final float BORDER = 3f;

    public static void render(SpriteBatch batch, ShapeDrawer drawer, World world) {
        drawHealthOilBars(batch, drawer, world.getRobot());
        drawWaveInfo(batch, world.getEnemyManager());
    }

    private static void drawWaveInfo(SpriteBatch batch, EnemyManager manager) {
        float x = (Gdx.graphics.getWidth() / 2f) - 25;

        Font.setData(Font.getSmallFont(), Color.WHITE, 1f);
        Font.setData(Font.getFont(), Color.WHITE, 1f);

        Font.drawCenter(Font.getSmallFont(), x, 70f, 50f, "Wave", batch);
        Font.drawCenter(x, 50f, 50f, ""+manager.getWave(), batch);
    }

    private static void drawHealthOilBars(SpriteBatch batch, ShapeDrawer drawer, Robot robot) {
        String healthText = robot.getHealth() + " / " + robot.getMaxHealth();
        String oilText = ((int) robot.getOilLevel()) + " / " + ((int) robot.getMaxOil()) + " Oil";
        float healthPercent = (float) robot.getHealth() / robot.getMaxHealth();
        float oilPercent = robot.getOilLevel() / robot.getMaxOil();

        float padding = 30;
        float healthX = (Gdx.graphics.getWidth() / 2f) - WIDTH - padding;
        float oilX = (Gdx.graphics.getWidth() / 2f) + padding;

        drawBar(healthX, healthPercent, healthText, Color.RED, batch, drawer);
        drawBar(oilX, oilPercent, oilText,  new Color(0x9f9f9fff), batch, drawer);
    }

    private static void drawBar(float x, float percent, String text, Color bar, SpriteBatch batch, ShapeDrawer drawer) {
        float y = 25;

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x - BORDER, y - BORDER, WIDTH + (BORDER * 2), HEIGHT + (BORDER * 2));
        drawer.setColor(bar);
        drawer.filledRectangle(x, y, percent * WIDTH, HEIGHT);

        drawText(x, y, batch, text);
    }

    private static void drawText(float x, float y, SpriteBatch batch, String text) {
        y += HEIGHT - 10;

        Font.setData(Color.BLACK, 1.0f);
        Font.drawCenter(x, y, WIDTH, text, batch);
    }
}
