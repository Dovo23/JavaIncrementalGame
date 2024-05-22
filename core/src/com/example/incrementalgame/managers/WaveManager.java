package com.example.incrementalgame.managers;

import com.badlogic.gdx.utils.Array;
import com.example.incrementalgame.entities.Enemy;
import com.example.incrementalgame.entities.Player;

public class WaveManager {
    private int waveNumber;
    private float healthMultiplier;
    private float damageMultiplier;
    private EntityManager entityManager;

    public WaveManager() {
        this.waveNumber = 1;
        this.healthMultiplier = 1.0f;
        this.damageMultiplier = 1.0f;
    }

    public void initialize(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void startNextWave() {
        waveNumber++;
        healthMultiplier = 1.0f + 0.1f * waveNumber;
        damageMultiplier = 1.0f + 0.1f * waveNumber;

        spawnEnemies();
    }

    private void spawnEnemies() {
        Array<Enemy> enemies = entityManager.getEnemies();
        enemies.clear();

        Enemy enemy1 = new Enemy(700, 20, 64, 64, (int)(100 * healthMultiplier), (int)(5 * damageMultiplier));
        Enemy enemy2 = new Enemy(600, 20, 64, 64, (int)(120 * healthMultiplier), (int)(7 * damageMultiplier));

        entityManager.addEnemy(enemy1);
        entityManager.addEnemy(enemy2);
    }

    public int getWaveNumber() {
        return waveNumber;
    }
}
