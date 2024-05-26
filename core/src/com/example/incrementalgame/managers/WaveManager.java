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
    //setting reference to the entity manager
    public void initialize(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void startNextWave() {
        waveNumber++;
        //checking and updating highest wave
        if (highestWave < waveNumber) {
            highestWave = waveNumber;
        }
        //updating multipliers based on wave number
        updateMultipliers();
        //spawning enemies with adjusted multipliers based on current wave
        entityManager.spawnEnemies(healthMulti, damageMulti, expMulti);
    }
    
    //in case I want to add more methods like decreasing wave
    private void updateMultipliers() {
        //increases multipliers by 10% each wave
        healthMulti = 1.0f + 0.1f * waveNumber;
        damageMulti = 1.0f + 0.1f * waveNumber;
        expMulti = 1.0f + 0.1f * waveNumber;
    }


    public int getWaveNumber() {
        return waveNumber;
    }

    //increases multiplier by 1% each wave
    public float getCurrentWaveMultiplier() {
        return 1 + (highestWave * 0.01f); 
    }

    public void setWave(int wave) {
        this.waveNumber = wave;
    }

    public float getExpMulti() {
        return expMulti;
    }
}
