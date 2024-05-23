package com.example.incrementalgame.managers;



public class WaveManager {
    private int waveNumber;
    private int highestWave;
    private float healthMulti;
    private float damageMulti;
    private float expMulti;
    private EntityManager entityManager;

    public WaveManager() {
        this.waveNumber = 1;
        this.highestWave = 1;
        this.healthMulti = 1.0f;
        this.damageMulti = 1.0f;
        this.expMulti = 1.0f;
    }

    public void initialize(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void startNextWave() {
        waveNumber++;
        if (highestWave < waveNumber) {
            highestWave = waveNumber;
        }
        updateMultipliers();
        entityManager.spawnEnemies(healthMulti, damageMulti, expMulti);
    }
    

    private void updateMultipliers() { // in case I want to add more methods like decreasing wave
        healthMulti = 1.0f + 0.1f * waveNumber;
        damageMulti = 1.0f + 0.1f * waveNumber;
        expMulti = 1.0f + 0.1f * waveNumber;
    }


    public int getWaveNumber() {
        return waveNumber;
    }

    public float getCurrentWaveMultiplier() {
        return 1 + (highestWave * 0.01f); // Increase multiplier by 1% each wave
    }

    public void setWave(int wave) {
        this.waveNumber = wave;
    }

    public float getExpMulti() {
        return expMulti;
    }
}
