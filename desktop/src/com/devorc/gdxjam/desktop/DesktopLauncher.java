package com.devorc.gdxjam.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.devorc.gdxjam.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Robogeddon";
		config.addIcon("icon.png", Files.FileType.Internal);

		new LwjglApplication(new Game(), config);
	}
}
