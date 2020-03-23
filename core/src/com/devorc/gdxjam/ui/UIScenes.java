package com.devorc.gdxjam.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public enum UIScenes {

    MAIN_MENU(null),
    IN_GAME(new Container<>()),
    GAME_OVER(new GameOverScreen());

    private final WidgetGroup component;

    UIScenes(WidgetGroup actor) {
        this.component = actor;
    }

    public WidgetGroup getComponent() {
        return component;
    }
}
