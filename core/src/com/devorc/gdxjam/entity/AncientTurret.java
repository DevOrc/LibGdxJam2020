package com.devorc.gdxjam.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devorc.gdxjam.Robot;
import com.devorc.gdxjam.world.World;

public class AncientTurret extends Enemy{

    private static final int RANGE_SQUARED = 200 * 200;
    private static final int RESET_TIME = 120;
    private static final int HEALTH = 25;

    static Texture texture;
    int turretTick;

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
        }
    }

    private void fireAtRobot() {
        Robot robot = world.getRobot();
        Vector2 turretPos = new Vector2(x, y);
        Vector2 robotPos = new Vector2(robot.getX(), robot.getY());

        float bulletAngle = robotPos.sub(turretPos).angleRad();
        float bulletOffset = getRadius() + 20;
        float x = (float) (this.x + (bulletOffset * Math.cos(bulletAngle)));
        float y = (float) (this.y + (bulletOffset * Math.sin(bulletAngle)));

        Gdx.app.postRunnable(() -> world.addEntity(new Bullet(x, y, bulletAngle, 1500)));
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
        batch.draw(texture, x - getRadius(), y - getRadius());
    }

    @Override
    public float getRadius() {
        return texture.getWidth() / 2f;
    }
}
