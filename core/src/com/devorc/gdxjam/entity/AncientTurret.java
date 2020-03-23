package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.GameRenderer;
import com.devorc.gdxjam.robot.Robot;
import com.devorc.gdxjam.world.World;

public class AncientTurret extends Enemy{

    private static final int RANGE_SQUARED = 200 * 200;
    private static final int RESET_TIME = 120;
    private static final int HEALTH = 25;

    static Texture texture;
    static Texture turretTexture;

    float turretAngle = 0f;
    int turretTick = RESET_TIME;

    public AncientTurret() {
        super(HEALTH);

        x = World.WORLD_PIXEL_SIZE / 2f;
        y = World.WORLD_PIXEL_SIZE / 2f + 150;
    }

    @Override
    public void update() {
        double robotDistance = getRobotDistanceSquared();

        if(turretTick <= RESET_TIME){
            turretTick++;
        }

        if(robotDistance <= RANGE_SQUARED && turretTick >= RESET_TIME){
            fireAtRobot();
        }else if(robotDistance >= RANGE_SQUARED){
            turretAngle += .01f;
        }else{
            Robot robot = world.getRobot();
            Vector2 turretPos = new Vector2(x, y);
            Vector2 robotPos = new Vector2(robot.getX(), robot.getY());

            turretAngle = robotPos.sub(turretPos).angleRad();
        }
    }

    private void fireAtRobot() {
        Robot robot = world.getRobot();
        Vector2 turretPos = new Vector2(x, y);
        Vector2 robotPos = new Vector2(robot.getX(), robot.getY());

        turretAngle = robotPos.sub(turretPos).angleRad();
        float bulletOffset = getRadius() + 20;
        float x = (float) (this.x + (bulletOffset * Math.cos(turretAngle)));
        float y = (float) (this.y + (bulletOffset * Math.sin(turretAngle)));

        Gdx.app.postRunnable(() -> world.addEntity(new Bullet(x, y, turretAngle, 1500)));
        turretTick = 0;
    }

    private float getRobotDistanceSquared() {
        Robot robot = world.getRobot();
        Vector2 turretPos = new Vector2(x, y);
        Vector2 robotPos = new Vector2(robot.getX(), robot.getY());

        return turretPos.dst2(robotPos);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        float turretAngleDegrees = -90 + (float) (turretAngle * 180 / Math.PI);

        batch.draw(texture, x - getRadius(), y - getRadius());
        GameRenderer.drawRotated(batch, turretTexture, x - getRadius(), y - getRadius(), turretAngleDegrees);
    }

    @Override
    public float getRadius() {
        return texture.getWidth() / 2f;
    }
}
