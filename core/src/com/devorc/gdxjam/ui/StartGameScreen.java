package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.devorc.gdxjam.Game;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;

public class StartGameScreen extends Table {

    private static final String text = "As a result of WWIII, the entire surface of Earth became inhabitable due to " +
            "large amounts of nuclear waste. To solve the issues, humanity lived in bunkers and built robots to fly " +
            "around and cleanup the waste. Unfortunately, all of the humans died before the cleanup was complete, so " +
            "now the robots just fly around trying to survive!" +
            "\n\nYou can use WASD to fly around, left click to shoot, and right click to mine." +
            " As you mine ores and fight enemies, you can upgrade your robot using the upgrade menu. " +
            "You will need to also collect oil once you upgrade your robot to have a shield. " +
            "\n\n To pause the game, press space. ";

    private final Game game = (Game) Gdx.app.getApplicationListener();

    public StartGameScreen() {
        super(VisUI.getSkin());

        Label welcome = new VisLabel("Welcome!", Styles.mediumLabel);
        Label textLabel = new VisLabel(text);
        textLabel.setWrap(true);

        TextButton button = new TextButton("Start Game!", Styles.buttons);
        UI.addClickListener(button, this::startGame);

        add(welcome).center().pad(15).row();
        add(textLabel).width(480).row();
        add().grow().row();
        add(button).center().growX().minHeight(50).pad(15).row();

        setSize(500, 430);
        setBackground(new ColorDrawable(new Color(0x2a2a2aff), Styles.border));
    }

    private void startGame(InputEvent event) {
        setVisible(false);
        game.setPaused(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setX((Gdx.graphics.getWidth() / 2f) - (getWidth() / 2f));
        setY((Gdx.graphics.getHeight() / 2f) - (getHeight() / 2f));
    }
}
