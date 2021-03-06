package com.devorc.gdxjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.devorc.gdxjam.entity.Enemy;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.ui.Font;
import com.devorc.gdxjam.ui.Styles;
import com.devorc.gdxjam.ui.UI;
import com.devorc.gdxjam.ui.UIScenes;
import com.devorc.gdxjam.world.Block;
import com.devorc.gdxjam.world.Floor;
import com.devorc.gdxjam.world.MainMenuWorld;
import com.devorc.gdxjam.world.World;
import com.kotcrab.vis.ui.VisUI;

public class Game extends ApplicationAdapter {

	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;

	private GameRenderer renderer;
	private UI ui;
	private World world;
	private World mainMenuWorld;

	private boolean firstGame = true;
	private boolean paused = false;

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
		mainMenuWorld = new MainMenuWorld(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(world != null && ui.getScene() != UIScenes.MAIN_MENU){
			if(!paused)
				world.update();
			renderer.render();
		}else if(ui.getScene() == UIScenes.MAIN_MENU){
			mainMenuWorld.update();
			renderer.renderMainMenuWorld(mainMenuWorld);
		}

		ui.render();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			paused = !paused;
			Gdx.app.log("DEBUG", "Paused: " + paused);
		}
	}

	@Override
	public void resize(int width, int height) {
		renderer.onResize(width, height);
		ui.resize(width, height);
	}

	public void startGame(boolean insane){
		world = new World(this);
		ui.setScene(UIScenes.IN_GAME);

		if(firstGame){ //Pause for first game because the Start Game Screen will need to show
			paused = true;
			firstGame = false;
		}else{
			paused = false;
		}

		if(insane){
			world.getRobot().getStats().maxAll();
			world.getRobot().maxShields();
			world.getEnemyManager().setWave(30);
		}
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

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
