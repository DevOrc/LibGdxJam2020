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

        TextButton restartButton = new VisTextButton("Restart: Normal Mode", Styles.buttons);
        UI.addClickListener(restartButton, event -> restartGame(false));
        TextButton restartInsaneButton = new VisTextButton("Restart: Insane Mode", Styles.buttons);
        UI.addClickListener(restartInsaneButton, event -> restartGame(true));
        TextButton mainMenuButton = new VisTextButton("Main Menu", Styles.buttons);
        UI.addClickListener(mainMenuButton, event -> gotoMainMenu());
        TextButton quitButton = new VisTextButton("Quit!", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        add(label).padTop(100).row();
        add().grow().row();
        add(restartButton).center().size(300, 50).growX().padBottom(20).row();
        add(restartInsaneButton).center().size(300, 50).growX().padBottom(20).row();
        add(mainMenuButton).center().size(300, 50).growX().padBottom(20).row();
        add(quitButton).center().size(300, 50).growX().padBottom(100);
    }

    private void gotoMainMenu() {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.getUI().setScene(UIScenes.MAIN_MENU);
    }

    private void restartGame(boolean insane) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.startGame(insane);
    }
}
