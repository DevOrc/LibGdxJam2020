package com.devorc.gdxjam.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.Item;
import com.devorc.gdxjam.ui.ColorDrawable;
import com.devorc.gdxjam.ui.Styles;
import com.devorc.gdxjam.ui.UI;
import com.devorc.gdxjam.world.World;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.Map;

public class RobotUpgradeMenu extends Table {

    private final Game game;
    private World prevWorld;

    public RobotUpgradeMenu() {
        super(VisUI.getSkin());
        setBackground(new ColorDrawable(Color.BLACK, Styles.border));
        game = (Game) Gdx.app.getApplicationListener();

        Gdx.app.postRunnable(this::updateMenu);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(prevWorld != game.getWorld()){
            updateMenu();
            prevWorld = game.getWorld();
        }

        this.setX(-2);
        this.setY(-2);

        getChildren().forEach(child -> {
            if(child.getUserObject() instanceof Runnable){
                ((Runnable) child.getUserObject()).run();
            }
        });
    }

    private void updateMenu() {
        if(game.getWorld() == null)
            return;

        RobotStats stats = game.getWorld().getRobot().getStats();

        clear();
        add("Upgrades").pad(5).colspan(3).center().row();
        for(RobotStats.RobotStat stat: stats.getStats()){
            VisTextButton upgradeButton = new VisTextButton(getButtonText(stat));
            upgradeButton.setUserObject((Runnable) () -> updateButton(upgradeButton, stat));
            UI.addClickListener(upgradeButton, event -> upgrade(stat));

            add(stat.getName() + ": ").pad(5).padLeft(10);
            add(Integer.toString(stat.getLevel())).padLeft(8).pad(5);
            add(upgradeButton).pad(10);
            row();
        }

        pack();
    }

    private void upgrade(RobotStats.RobotStat stat) {
        game.getWorld().changeItemAmount(stat.getRequirement(), -stat.getCost());
        stat.onUpgrade();
        updateMenu();
    }

    private void updateButton(VisTextButton button, RobotStats.RobotStat stat) {
        Map<Item, Integer> inventory = game.getWorld().getInventory();

        button.setDisabled(inventory.get(stat.getRequirement()) < stat.getCost());
    }

    private String getButtonText(RobotStats.RobotStat stat) {
        if(stat.getCost() == Integer.MAX_VALUE){
            return "MAXED OUT!";
        }

        return "Upgrade ("+ stat.getCost() + " " + stat.getRequirement().name() + ")";
    }
}
