package com.example.incrementalgame.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.GameButton;

public class PrestigeManager {
    private ResourceManager resourceManager;
    private BuildingManager buildingManager;
    private EntityManager entityManager;
    private WaveManager waveManager;
    private GameButton prestigeButton;
    private Assets assets;

    private int prestigeLevel = 0;
    private double nextPrestigeRequirement = 5000;

    public PrestigeManager(Assets assets, ResourceManager resourceManager, BuildingManager buildingManager, EntityManager entityManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.buildingManager = buildingManager;
        this.entityManager = entityManager;
        this.waveManager = waveManager;

        entityManager.initialize(this);
    }

    public void create() {
         prestigeButton = new GameButton(650, 150, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Prestige");
    }

    public void render(SpriteBatch batch) {
        //prestige button appears if requirement met
        if (resourceManager.getGold() >= nextPrestigeRequirement) {
            prestigeButton.draw(batch, assets.font, assets.buttonTexture);
        }
    }

    public void initialize(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void handleButtonInput(float x, float y) {
        if (prestigeButton.getBounds().contains(x, y) && resourceManager.getGold() >= nextPrestigeRequirement) {
            performPrestige();
        }
    }
    
    public void performPrestige() {
        resourceManager.setGold(100);
        buildingManager.getBuildingByName("Miner").resetWithMultiplier();
        buildingManager.getBuildingByName("Bakery").resetWithMultiplier();
        buildingManager.getBuildingByName("Factory").resetWithMultiplier();

        entityManager.getPlayer().resetAge();
        entityManager.getPlayer().resetStats();

        waveManager.setWave(1);
        resourceManager.setExpMultiplier(waveManager.getCurrentWaveMultiplier());

        prestigeLevel++;
        nextPrestigeRequirement *= 2; 
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }
 
    public double getNextPrestigeRequirement() {
        return nextPrestigeRequirement;
    }

    

}
