package com.devorc.gdxjam;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameRenderer {

    private final SpriteBatch batch = new SpriteBatch();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ScreenViewport viewport = new ScreenViewport(camera);
    private final Game game;

    public GameRenderer(Game game) {
        this.game = game;
    }

    public void render(){
        syncCameraToPlayer();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        game.getWorld().render(batch);
        batch.end();
    }

    private void syncCameraToPlayer() {
        camera.position.x = game.getWorld().getRobot().getX();
        camera.position.y = game.getWorld().getRobot().getY();
    }

    public void onResize(int width, int height){
        viewport.update(width, height);
    }

    public static void drawRotated(SpriteBatch batch, Texture texture, float x, float y, float degrees){
        int width = texture.getWidth();
        int height = texture.getHeight();

        batch.draw(texture, x, y, width / 2f, height / 2f, width, height,
                1, 1, degrees, 0, 0, width, height, false, false);
    }
}
