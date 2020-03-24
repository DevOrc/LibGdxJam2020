package com.devorc.gdxjam.world;

import com.badlogic.gdx.Gdx;
import com.devorc.gdxjam.entity.AncientTurret;
import com.devorc.gdxjam.entity.Bomber;
import com.devorc.gdxjam.entity.Enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EnemyManager {

    private final Random random = new Random();
    private final List<Enemy> enemies = new LinkedList<>();
    private final World world;

    private int deadEnemies = 0;

    private long nextWaveTime = System.currentTimeMillis();
    int enemyCount = 2;

    private int wave = 1;

    public EnemyManager(World world) {
        this.world = world;
    }

    public void add(Enemy enemy){
        enemies.add(enemy);
    }

    void update(){
        removeDeadEnemies();

        if(enemies.size() < enemyCount){
            spawnEnemy();
            Gdx.app.log("DEBUG", "Spawned Enemy");
        }

        if(nextWaveTime < System.currentTimeMillis()){
            nextWaveTime += 15000;
            wave++;
            enemyCount = 2 + (wave / 2);
        }
    }

    private void spawnEnemy() {
        int id = random.nextInt(10);

        if(id < 7){
            world.addEntity(new AncientTurret(world));
        }else{
            world.addEntity(new Bomber());
        }
    }

    private void removeDeadEnemies() {
        int originalSize = enemies.size();
        enemies.removeIf(Enemy::isDead);
        deadEnemies += originalSize - enemies.size();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getWave() {
        return wave;
    }
}
