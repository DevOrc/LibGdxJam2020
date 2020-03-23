package com.devorc.gdxjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class Styles {

    static final Color border = new Color(0x48b3e8ff);

    static Label.LabelStyle title;
    static VisTextButton.VisTextButtonStyle buttons;

    public static void init(){
        title = new Label.LabelStyle(Font.getTitleFont(), Color.WHITE);

        buttons = new VisTextButton.VisTextButtonStyle();
        buttons.over = new ColorDrawable(new Color(0x3a3a3aff), border);
        buttons.up = new ColorDrawable(new Color(0x3f3f3fff), border);
        buttons.down = new ColorDrawable(new Color(0x2f2f2fff), border);
        buttons.font = Font.getButtonFont();
    }

}
