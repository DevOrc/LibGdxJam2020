package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.world.World;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class GameOverScreen extends Table {

    protected final Game game = (Game) Gdx.app.getApplicationListener();

    private final Label statsLabel;

    public GameOverScreen() {
        super(VisUI.getSkin());

        VisLabel label = new VisLabel(getTitle(), Styles.title);
        statsLabel = new VisLabel("", Styles.mediumLabel);
        statsLabel.setAlignment(Align.center);

        TextButton restartButton = new VisTextButton("Start Normal", Styles.buttons);
        UI.addClickListener(restartButton, event -> restartGame(false));
        TextButton restartInsaneButton = new VisTextButton("Start Insane", Styles.buttons);
        restartInsaneButton.addListener(UI.insaneTooltip);
        UI.addClickListener(restartInsaneButton, event -> restartGame(true));
        TextButton mainMenuButton = new VisTextButton("Main Menu", Styles.buttons);
        UI.addClickListener(mainMenuButton, event -> gotoMainMenu());
        TextButton quitButton = new VisTextButton("Quit!", Styles.buttons);
        UI.addClickListener(quitButton, event -> Gdx.app.exit());

        add(label).padTop(100).colspan(2).row();
        add(statsLabel).padTop(15).colspan(2).row();
        add().grow().colspan(2).row();
        addResumeButton();
        add(restartButton).center().size(300, 50).align(Align.right).padBottom(20).padRight(10);
        add(restartInsaneButton).center().size(300, 50).align(Align.left).padBottom(20).row();
        add(mainMenuButton).center().size(300, 50).align(Align.right).padBottom(100).padRight(10);
        add(quitButton).center().size(300, 50).align(Align.left).padBottom(100);
    }

    protected void addResumeButton() {
        //Im sorry this code is really ugly
        //This function is used by the Paused Menu class
    }

    protected String getTitle(){
        return "Game Over";
    }

    private void gotoMainMenu() {
        game.getUI().setScene(UIScenes.MAIN_MENU);
    }

    private void restartGame(boolean insane) {
        game.startGame(insane);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(game.getWorld() != null){
            String stats = getEndGameStats(game.getWorld());

            statsLabel.setText(stats);
        }
    }

    protected String getEndGameStats(World world) {
        StringBuilder builder = new StringBuilder();
        long gameTime = world.getGameTime() / 1000;
        int wave = world.getEnemyManager().getWave();
        int enemiesKilled = world.getEnemyManager().getDeadEnemies();

        builder.append("Wave: ").append(wave).append("\n");
        builder.append("Enemies Killed: ").append(enemiesKilled).append("\n");
        builder.append("Time Survived: ").append(gameTime).append(" seconds\n");

        return builder.toString();
    }
}
