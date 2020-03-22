package com.devorc.gdxjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Floor;
import com.devorc.gdxjam.world.World;

public class Game extends ApplicationAdapter {

	private GameRenderer renderer;
	private World world;

	@Override
	public void create () {
		renderer = new GameRenderer(this);
		world = new World(this);

		Robot.loadTexture();
		Floor.loadTextures();
		Block.loadTextures();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();
		world.update();
	}

	@Override
	public void resize(int width, int height) {
		renderer.onResize(width, height);
	}

	@Override
	public void dispose () {

	}

	public World getWorld() {
		return world;
	}

	public GameRenderer getRenderer() {
		return renderer;
	}
}
