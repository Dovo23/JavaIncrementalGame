package com.example.incrementalgame.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.GameButton;

public class PrestigeManager {
    private ResourceManager resourceManager;
    private BuildingManager buildingManager;
    private GameButton prestigeButton;
    private Assets assets;

    private int prestigeLevel = 0;
    private double nextPrestigeRequirement = 5000;

    public PrestigeManager(Assets assets, ResourceManager resourceManager, BuildingManager buildingManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.buildingManager = buildingManager;
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
    
    public void handleButtonInput(float x, float y) {
        if (prestigeButton.getBounds().contains(x, y) && resourceManager.getGold() >= nextPrestigeRequirement) {
            performPrestige();
        }
    }
    
    private void performPrestige() {
        resourceManager.setGold(100);
        buildingManager.getBuildingByName("Miner").resetWithMultiplier();
        buildingManager.getBuildingByName("Bakery").resetWithMultiplier();
        buildingManager.getBuildingByName("Factory").resetWithMultiplier();
        prestigeLevel++;
        nextPrestigeRequirement *= 2; // Double the requirement for the next prestige
    }

    public int getPresigeLevel() {
        return prestigeLevel;
    }
 
    public double getNextPrestigeRequirement() {
        return nextPrestigeRequirement;
    }

}
