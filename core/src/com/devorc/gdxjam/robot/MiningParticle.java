package com.devorc.gdxjam.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MiningParticle {

    private static ParticleEffect effect = new ParticleEffect();

    public static void loadParticle(){
        Texture texture = new Texture("mining_particle.png");
        TextureAtlas atlas = new TextureAtlas();
        atlas.addRegion("mining_particle", texture, 0, 0, 32, 32);
        effect.load(Gdx.files.internal("mining_particle"), atlas);
    }

    public static void start(){
        effect.start();
    }

    public static void draw(SpriteBatch batch){
        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public static void update(float x, float y) {
        effect.setPosition(x, y);

        if(effect.isComplete()){
            effect.start();
        }
    }

    public static void reset() {
        effect.reset();
    }

    public static ParticleEffect getEffect() {
        return effect;
    }
}
