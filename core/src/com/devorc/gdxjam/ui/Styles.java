package com.devorc.gdxjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class Styles {

    public static final Color border = new Color(0x48b3e8ff);

    public static Label.LabelStyle title;
    public static Label.LabelStyle mediumLabel;
    public static VisTextButton.VisTextButtonStyle buttons;

    public static void init(){
        title = new Label.LabelStyle(Font.getTitleFont(), Color.WHITE);
        mediumLabel = new Label.LabelStyle(Font.getFont(), Color.WHITE);

        buttons = new VisTextButton.VisTextButtonStyle();
        buttons.over = new ColorDrawable(new Color(0x3a3a3aff), border);
        buttons.up = new ColorDrawable(new Color(0x3f3f3fff), border);
        buttons.down = new ColorDrawable(new Color(0x2f2f2fff), border);
        buttons.font = Font.getButtonFont();
    }

}
