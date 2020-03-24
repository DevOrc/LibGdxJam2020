package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.devorc.gdxjam.Game;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MainMenu extends Table {

    private static final Drawable background = new ColorDrawable(new Color(0x111111ff));

    public MainMenu() {
        super(VisUI.getSkin());
        setBackground(background);

        VisLabel label = new VisLabel("Title Here", Styles.title);

        TextButton restartButton = new VisTextButton("Start", Styles.buttons);
        UI.addClickListener(restartButton, event -> startGame());
        TextButton quitButton = new VisTextButton("Quit", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        add(label).padTop(100).row();
        add().grow().row();
        add(restartButton).center().size(300, 50).growX().padBottom(20).row();
        add(quitButton).center().size(300, 50).growX().padBottom(100);
    }

    private void startGame() {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.startGame();
    }


}
