package com.devorc.gdxjam.entity;

import com.badlogic.gdx.graphics.Texture;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.World;

import java.util.Random;
import java.util.function.Consumer;

public enum Powerups {

    HEALTH("health", robot -> robot.heal(25));

    private final Texture texture;
    private final Consumer<Robot> onPickup;

    Powerups(String name, Consumer<Robot> onPickup) {
        this.onPickup = onPickup;
        this.texture = new Texture("powerup_" + name + ".png");
    }

    public void spawn(World world){
        world.addEntity(new Powerup(texture, world, onPickup));
    }

    public static void spawnRandom(World world){
        Random random = new Random();

        values()[random.nextInt(values().length)].spawn(world);
    }
}
