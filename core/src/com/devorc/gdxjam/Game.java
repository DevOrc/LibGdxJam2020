package com.devorc.gdxjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.devorc.gdxjam.entity.Enemy;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.ui.Font;
import com.devorc.gdxjam.ui.Styles;
import com.devorc.gdxjam.ui.UI;
import com.devorc.gdxjam.ui.UIScenes;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Floor;
import com.devorc.gdxjam.world.World;
import com.kotcrab.vis.ui.VisUI;

public class Game extends ApplicationAdapter {

	private GameRenderer renderer;
	private UI ui;
	private World world;

	@Override
	public void create () {
		VisUI.load();
		Font.loadFont();
		Sounds.load();
		Robot.loadTexture();
		Enemy.loadTextures();
		Floor.loadTextures();
		Block.loadTextures();
		Item.loadTextures();
		Styles.init();

		renderer = new GameRenderer(this);
		ui = new UI(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(world != null){
			world.update();
			renderer.render();
		}

		ui.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.onResize(width, height);
		ui.resize(width, height);
	}

	public void startGame(){
		world = new World(this);
		ui.setScene(UIScenes.IN_GAME);
	}

	@Override
	public void dispose () {
		ui.dispose();
		VisUI.getSkin().dispose();
	}

	public World getWorld() {
		return world;
	}

	public GameRenderer getRenderer() {
		return renderer;
	}

	public UI getUI() {
		return ui;
	}
}
