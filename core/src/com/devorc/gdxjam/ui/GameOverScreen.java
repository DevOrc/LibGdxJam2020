package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.devorc.gdxjam.Game;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class GameOverScreen extends Table {

    public GameOverScreen() {
        super(VisUI.getSkin());

        VisLabel label = new VisLabel("Game Over!", Styles.title);

        TextButton restartButton = new VisTextButton("Restart", Styles.buttons);
        UI.addClickListener(restartButton, event -> restartGame());
        TextButton quitButton = new VisTextButton("Quit", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        add(label).padTop(100).row();
        add().grow().row();
        add(restartButton).center().size(300, 50).growX().padBottom(20).row();
        add(quitButton).center().size(300, 50).growX().padBottom(100);
    }

    private void restartGame() {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.restart();
    }
}
