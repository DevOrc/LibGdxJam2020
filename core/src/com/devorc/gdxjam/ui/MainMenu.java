package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.GLVersion;
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
    private final Table settingsButtons = new Table();
    private final Table aboutTable = new Table(VisUI.getSkin());

    public MainMenu() {
        super(VisUI.getSkin());
        createMainButtons();
        createStartButtons();
        createSettingsButtons();
        createAboutMenu();

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
        TextButton aboutButton = new VisTextButton("About", Styles.buttons);
        UI.addClickListener(aboutButton, event -> buttonCell.setActor(aboutTable));
        TextButton settingsButton = new VisTextButton("Settings", Styles.buttons);
        UI.addClickListener(settingsButton, event -> buttonCell.setActor(settingsButtons));
        TextButton quitButton = new VisTextButton("Quit", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        mainButtons.add(startButton).center().size(310, 50).growX().padBottom(15).colspan(2).row();
        mainButtons.add(aboutButton).center().size(150, 50).growX().padBottom(15).padRight(5);
        mainButtons.add(settingsButton).center().size(150, 50).growX().padBottom(15).padLeft(5).row();
        mainButtons.add(quitButton).center().size(310, 50).colspan(2).growX();
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

    private void createAboutMenu(){
        aboutTable.add().padTop(10).row();

        for(String info: getInfo()){
            aboutTable.add(info).padLeft(20).padRight(20).row();
        }

        TextButton backButton = new VisTextButton("Back", Styles.buttons);
        UI.addClickListener(backButton, event -> buttonCell.setActor(mainButtons));

        aboutTable.setBackground(new ColorDrawable(new Color(0x2a2a2aff), Styles.border));
        aboutTable.add(backButton).center().pad(20).size(100, 30).growX();
    }

    private String[] getInfo() {
        GLVersion glInfo = Gdx.graphics.getGLVersion();

        return new String[]{
                "Author: Noah Charlton",
                "Game Version: 0.1.0",
                "LibGDX Version: " + Version.VERSION,
                "GL Version: " + glInfo.getMajorVersion() + "." + glInfo.getMinorVersion(),
                "Vendor: " + glInfo.getVendorString(),
                "Java Version: " + System.getProperty("java.version"),
                "OS: " + System.getProperty("os.name")
        };
    }

    private void createSettingsButtons(){
        TextButton fullscreenButton = new VisTextButton("Toggle Fullscreen", Styles.buttons);
        UI.addClickListener(fullscreenButton, event -> toggleFullscreen());
        TextButton backButton = new VisTextButton("Back", Styles.buttons);
        UI.addClickListener(backButton, event -> buttonCell.setActor(mainButtons));

        settingsButtons.add(fullscreenButton).center().size(300, 50).growX().padBottom(20).row();
        settingsButtons.add(backButton).center().size(100, 50).growX();
    }

    private void toggleFullscreen() {
        if(Gdx.graphics.isFullscreen()){
            Gdx.graphics.setWindowedMode(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT);
        }else{
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }

    private void startGame(boolean insane) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.startGame(insane);

        buttonCell.setActor(mainButtons);
    }


}
