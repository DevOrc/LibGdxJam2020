package com.devorc.gdxjam.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.devorc.gdxjam.robot.RobotUpgradeMenu;

public class InGameUI extends Stack {

    public InGameUI() {
        add(new RobotUpgradeMenu());
    }

    @Override
    public void layout() {
        Array<Actor> children = getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = children.get(i);
            if (child instanceof Layout) ((Layout)child).validate();
        }
    }
}
