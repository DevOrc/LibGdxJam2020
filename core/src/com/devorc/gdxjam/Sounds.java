package com.devorc.gdxjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    public static final Sound EXPLOSION = loadSound("explosion.wav");

    private static Sound loadSound(String path) {
        return Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public static void load(){}
}
