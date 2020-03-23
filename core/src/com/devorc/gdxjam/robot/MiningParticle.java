package com.devorc.gdxjam.robot;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MiningParticle {

    static boolean isHTML = Gdx.app.getType() == Application.ApplicationType.WebGL;
    private static ParticleEffect effect = new ParticleEffect();

    public static void loadParticle() {
        if(isHTML)
            return;

        Texture texture = new Texture("mining_particle.png");
        TextureAtlas atlas = new TextureAtlas();
        atlas.addRegion("mining_particle", texture, 0, 0, 32, 32);
        effect.load(Gdx.files.internal("mining_particle"), atlas);
    }

    public static void start() {
        if(!isHTML) {
            effect.start();
        }
    }

    public static void draw(SpriteBatch batch) {
        if(!isHTML) {

            effect.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    public static void update(float x, float y) {
        if(!isHTML) {

            effect.setPosition(x, y);

            if(effect.isComplete()) {
                effect.start();
            }
        }
    }

    public static void reset() {
        if(!isHTML) {
            effect.reset();
        }
    }
}
