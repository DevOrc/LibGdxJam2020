package com.devorc.gdxjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter {

	private GameRenderer renderer;
	Robot robot;

	@Override
	public void create () {
		renderer = new GameRenderer(this);
		robot = new Robot();

		Robot.loadTexture();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();
		update();
	}

	private void update() {
		robot.update();
	}

	@Override
	public void resize(int width, int height) {
		renderer.onResize(width, height);
	}

	@Override
	public void dispose () {

	}
}
