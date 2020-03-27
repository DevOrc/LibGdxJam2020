package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.robot.RobotUpgradeMenu;

public class InGameUI extends Stack {

    private final PausedMenu pausedMenu = new PausedMenu();
    private final RobotUpgradeMenu upgradeMenu = new RobotUpgradeMenu();
    private final StartGameScreen startGameScreen = new StartGameScreen();

    private final Game game = (Game) Gdx.app.getApplicationListener();

    public InGameUI() {
        add(upgradeMenu);
        add(pausedMenu);
        add(startGameScreen);
    }

    @Override
    public void layout() {
        Array<Actor> children = getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = children.get(i);
            if (child instanceof Layout) ((Layout)child).validate();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        upgradeMenu.setVisible(!game.isPaused());
        pausedMenu.setVisible(game.isPaused() && !startGameScreen.isVisible());
        pausedMenu.setPosition(0, 0);
        pausedMenu.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
