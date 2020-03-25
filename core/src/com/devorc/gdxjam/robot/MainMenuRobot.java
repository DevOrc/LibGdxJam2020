package com.devorc.gdxjam.robot;

import com.devorc.gdxjam.Game;
import com.devorc.gdxjam.world.World;

import java.util.Random;

public class MainMenuRobot extends Robot {

    private final Random random = new Random();

    public MainMenuRobot(Game game) {
        super(game);
    }

    @Override
    protected void updateControls() {
        velocity = 330f;
        accelerating = true;

        if(getY() > World.WORLD_PIXEL_SIZE * .8f){
            angle = (float) (Math.PI * 3 / 2);
        }else if(getY() < World.WORLD_PIXEL_SIZE * .2f){
            angle = (float) (Math.PI / 2);
        }
    }

    @Override
    protected void checkCollision() {
    }

    @Override
    protected void fireBullet(float angle) {

    }

    @Override
    protected void updateShooter() {

    }

    @Override
    protected void runLaser() {

    }
}
