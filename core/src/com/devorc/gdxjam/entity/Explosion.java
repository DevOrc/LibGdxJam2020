package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.devorc.gdxjam.Sounds;
import com.devorc.gdxjam.world.World;

public class Explosion implements Entity {

    static boolean isHTML = Gdx.app.getType() == Application.ApplicationType.WebGL;

    public static ParticleEffectPool pool;

    private final ParticleEffect effect;

    public Explosion(float x, float y, float scale) {
        if(!isHTML){
            effect = pool.obtain();
            effect.setPosition(x, y);
            effect.scaleEffect(scale);
            effect.start();
        }else{
            effect = null;
        }


        Sounds.EXPLOSION.play(1.2f);
    }

    public Explosion(float x, float y) {
        this(x, y, .25f);
    }

    public static void loadEffect(){
        if(isHTML)
            return;

        ParticleEffect effect = new ParticleEffect();
        Texture texture = new Texture("particle.png");
        TextureAtlas atlas = new TextureAtlas();
        atlas.addRegion("particle", texture, 0, 0, 32, 32);
        effect.load(Gdx.files.internal("explosion"), atlas);
        effect.scaleEffect(.25f);

        pool = new ParticleEffectPool(effect, 5, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(isHTML)
            return;

        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void update() {}

    @Override
    public boolean isDead() {
        if(isHTML)
            return true;

        return effect.isComplete();
    }

    @Override
    public void setWorld(World world) {}
}
