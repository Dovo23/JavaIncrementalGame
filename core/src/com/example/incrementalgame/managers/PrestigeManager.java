package com.example.incrementalgame.managers;

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

    public PrestigeManager(Assets assets, ResourceManager resourceManager, BuildingManager buildingManager,
            EntityManager entityManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.buildingManager = buildingManager;
        this.entityManager = entityManager;
        this.waveManager = waveManager;

        entityManager.initialize(this);
    }

    //method to create the prestige button
    public void create() {
        prestigeButton = new GameButton(650, 150, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Prestige");
    }
    
     //setting reference to the entity manager
     public void initialize(EntityManager entityManager) {
         this.entityManager = entityManager;
     }
    
    //method to handle the input of the prestige button
    public void handleButtonInput(float x, float y) {
        if (prestigeButton.getBounds().contains(x, y) && resourceManager.getGold() >= nextPrestigeRequirement) {
            prestigeBuildings();
        }
    }

    public boolean isPrestigeAvailable() {
        return resourceManager.getGold() >= nextPrestigeRequirement;
    }

    public GameButton getPrestigeButton() {
        return prestigeButton;
    }
    
    //method to perform the prestige, resetting attributes and increasing multipliers
    public void performPrestige() {
        resourceManager.setGold(100);
        // buildingManager.getBuildingByName("Miner").resetWithMultiplier();
        // buildingManager.getBuildingByName("Bakery").resetWithMultiplier();
        // buildingManager.getBuildingByName("Factory").resetWithMultiplier();

        entityManager.getPlayer().resetAge();
        entityManager.getPlayer().resetStats();
        entityManager.getPlayer().resetPlayer();

        waveManager.setWave(1);
        resourceManager.setExpMultiplier(waveManager.getCurrentWaveMultiplier());
        System.out.println("Prestige performed. Player state reset.");

        // prestigeLevel++;
        // nextPrestigeRequirement *= 2;
    }
    
    public void prestigeBuildings() {
        // Reset building levels and increase production multiplier
        resourceManager.setGold(100);
        buildingManager.getBuildingByName("Wheat Field").resetWithMultiplier();
        buildingManager.getBuildingByName("Farm").resetWithMultiplier();
        buildingManager.getBuildingByName("Factory").resetWithMultiplier();

        System.out.println("Buildings have been prestiged!");

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
