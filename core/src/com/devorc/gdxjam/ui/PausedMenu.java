package com.devorc.gdxjam.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.devorc.gdxjam.world.World;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PausedMenu extends GameOverScreen{

    @Override
    protected void addResumeButton() {
        TextButton resumeButton = new VisTextButton("Resume", Styles.buttons);
        UI.addClickListener(resumeButton, event -> game.setPaused(false));

        add(resumeButton).center().colspan(2).size(330, 50).growX().padBottom(20).row();
    }

    @Override
    protected String getTitle() {
        return "Paused";
    }

    @Override
    protected String getEndGameStats(World world) {
        return "";
    }
}
