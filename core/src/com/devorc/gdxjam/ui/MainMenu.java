package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.devorc.gdxjam.Game;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MainMenu extends Table {

    private final Cell buttonCell;

    private final Table mainButtons = new Table();
    private final Table startButtons = new Table();

    public MainMenu() {
        super(VisUI.getSkin());
        createMainButtons();
        createStartButtons();

        VisLabel label = new VisLabel("Robogeddon", Styles.title);

        add(label).padTop(100).row();
        add().grow().row();
        buttonCell = add().padBottom(100);
        buttonCell.setActor(mainButtons);
        row();
    }

    private void createMainButtons(){
        TextButton startButton = new VisTextButton("Start", Styles.buttons);
        UI.addClickListener(startButton, event -> buttonCell.setActor(startButtons));
        TextButton quitButton = new VisTextButton("Quit", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        mainButtons.add(startButton).center().size(300, 50).growX().padBottom(20).row();
        mainButtons.add(quitButton).center().size(300, 50).growX();
    }

    private void createStartButtons(){
        TextButton startNormal = new VisTextButton("Start Normal Mode", Styles.buttons);
        UI.addClickListener(startNormal, event -> startGame(false));
        TextButton startInsane = new VisTextButton("Start Insane Mode", Styles.buttons);
        UI.addClickListener(startInsane, event -> startGame(true));
        TextButton backButton = new VisTextButton("Back", Styles.buttons);
        UI.addClickListener(backButton, event -> buttonCell.setActor(mainButtons));

        startButtons.add(startNormal).center().size(300, 50).growX().padBottom(20).row();
        startButtons.add(startInsane).center().size(300, 50).growX().padBottom(20).row();
        startButtons.add(backButton).center().size(100, 50).growX();
    }

    private void startGame(boolean insane) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.startGame(insane);

        buttonCell.setActor(mainButtons);
    }


}
