package com.devorc.gdxjam.ui;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public enum UIScenes {

    MAIN_MENU(new MainMenu()),
    IN_GAME(new InGameUI()),
    GAME_OVER(new GameOverScreen());

    private final WidgetGroup component;

    UIScenes(WidgetGroup actor) {
        this.component = actor;
    }

    public WidgetGroup getComponent() {
        return component;
    }
}
