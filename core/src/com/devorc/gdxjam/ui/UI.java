package com.devorc.gdxjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.devorc.gdxjam.Game;

import java.util.function.Consumer;

public class UI {

    private final Stage stage = new Stage(new ScreenViewport());
    private final Game game;

    private UIScenes scene;

    public UI(Game game) {
        this.game = game;

        Gdx.input.setInputProcessor(stage);

        setScene(UIScenes.MAIN_MENU);
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void setScene(UIScenes scene){
        Gdx.app.log("INFO", "Setting UI: " + scene.name());
        scene.getComponent().setFillParent(true);

        this.scene = scene;
        stage.getRoot().clear();
        stage.addActor(scene.getComponent());
    }

    public void render(){
        stage.act();
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }

    public static void addClickListener(Actor actor, Consumer<InputEvent> listener) {
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(actor instanceof Disableable){
                    Disableable disableable = (Disableable) actor;

                    if(disableable.isDisabled()){
                        return;
                    }
                }
                listener.accept(event);
            }
        });
    }

    public UIScenes getScene() {
        return scene;
    }
}
