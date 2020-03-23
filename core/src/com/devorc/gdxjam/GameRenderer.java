package com.devorc.gdxjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.devorc.gdxjam.ui.HealthBar;
import com.devorc.gdxjam.ui.InventoryUI;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameRenderer {

    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeDrawer shapeDrawer;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ScreenViewport viewport = new ScreenViewport(camera);

    private final SpriteBatch uiBatch = new SpriteBatch();
    private final ShapeDrawer uiShapeDrawer;
    private final ScreenViewport uiViewport = new ScreenViewport();

    private final Game game;

    public GameRenderer(Game game) {
        this.game = game;

        Pixmap map = new Pixmap(1, 1, Pixmap.Format.RGB565);

        map.setColor(Color.WHITE);
        map.drawPixel(0, 0);

        TextureRegion dot = new TextureRegion(new Texture(map));
        shapeDrawer = new ShapeDrawer(batch, dot);
        uiShapeDrawer = new ShapeDrawer(uiBatch, dot);
    }

    public void render() {
        syncCameraToPlayer();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        uiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Matrix4 uiMatrix = viewport.getCamera().projection;
        uiMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiBatch.setProjectionMatrix(uiMatrix);

        renderGame();

        if(game.getWorld().isGameOver()){
            drawShade();
        }else{
            renderUI();
        }
    }

    private void renderUI() {
        uiBatch.begin();
        InventoryUI.render(uiBatch, game.getWorld());
        HealthBar.render(uiBatch, uiShapeDrawer, game.getWorld().getRobot());
        uiBatch.end();
    }

    private void drawShade() {
        boolean originallyBlending = uiBatch.isBlendingEnabled();
        uiBatch.enableBlending();
        uiBatch.begin();
        uiShapeDrawer.setColor(0f, 0f, 0f, .6f);
        uiShapeDrawer.filledRectangle(0 ,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiBatch.end();

        if(!originallyBlending){
            uiBatch.disableBlending();
        }
    }

    private void renderGame() {
        batch.begin();
        game.getWorld().render(batch);
        batch.end();
    }

    private void syncCameraToPlayer() {
        camera.position.x = game.getWorld().getRobot().getX();
        camera.position.y = game.getWorld().getRobot().getY();
    }

    public void onResize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height);
    }

    public static void drawRotated(SpriteBatch batch, Texture texture, float x, float y, float degrees) {
        int width = texture.getWidth();
        int height = texture.getHeight();

        batch.draw(texture, x, y, width / 2f, height / 2f, width, height,
                1, 1, degrees, 0, 0, width, height, false, false);
    }

    public Vector3 unProject(int x, int y) {
        return camera.unproject(new Vector3(x, y, 0f));
    }

    public ShapeDrawer getShapeDrawer() {
        return shapeDrawer;
    }

    public SpriteBatch getUiBatch() {
        return uiBatch;
    }

    public ShapeDrawer getUiShapeDrawer() {
        return uiShapeDrawer;
    }
}
