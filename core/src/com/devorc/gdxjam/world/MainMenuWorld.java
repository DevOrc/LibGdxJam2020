package com.devorc.gdxjam.world;

import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.robot.MainMenuRobot;

public class MainMenuWorld extends World{

    public MainMenuWorld(Game game) {
        super(game, new MainMenuRobot(game));
    }

    @Override
    public void update() {
        super.update();

        getEntities().clear();
    }
}
