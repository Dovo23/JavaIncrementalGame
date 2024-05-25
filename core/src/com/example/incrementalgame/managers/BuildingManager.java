package com.example.incrementalgame.managers;

import com.badlogic.gdx.utils.Array;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Building;
import com.example.incrementalgame.entities.GameButton;

public class BuildingManager {
    private Array<Building> buildings;
    private GameButton buyMinerButton, buyBakeryButton, buyFactoryButton;
    private Assets assets;
    private ResourceManager resourceManager;

    public BuildingManager(Assets assets, ResourceManager resourceManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        
        buildings = new Array<>();
        addBuildings();
        addButtons();
    }

    private void addBuildings() {
        buildings.add(new Building("Miner", 10, 1));
        buildings.add(new Building("Bakery", 100, 10));
        buildings.add(new Building("Factory", 500, 100));
    }

    private void addButtons() {
        buyMinerButton = new GameButton(650, 360, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Miner " + getBuildingByName("Miner").getIncomePerSecondBase() + " gold/s");
        buyBakeryButton = new GameButton(650, 290, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Bakery " + getBuildingByName("Bakery").getIncomePerSecondBase() + " gold/s");
        buyFactoryButton = new GameButton(650, 220, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Factory " + getBuildingByName("Factory").getIncomePerSecondBase() + " gold/s");
    }

    public void update(float delta) {
        updateButtonLabels();
    }
    
    public void buyBuilding(String buildingName) {
        Building building = getBuildingByName(buildingName);
        if (building != null && resourceManager.getGold() >= building.getCost()) {
            resourceManager.subtractGold(building.getCost());
            building.increaseLevel();
        }
    }

    public Building getBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null; 
    }

    public void handleButtonInput(float x, float y) {
        if (buyMinerButton.getBounds().contains(x, y)) {
            buyBuilding("Miner");
        } else if (buyBakeryButton.getBounds().contains(x, y)) {
             buyBuilding("Bakery");
        } else if (buyFactoryButton.getBounds().contains(x, y)) {
             buyBuilding("Factory");
        }
    }
     
    private void updateButtonLabels() {
        buyMinerButton.setText("Buy Miner " + getBuildingByName("Miner").getIncomePerSecondBase() + " gold/s");
        buyBakeryButton.setText("Buy Bakery " + getBuildingByName("Bakery").getIncomePerSecondBase() + " gold/s");
        buyFactoryButton.setText("Buy Factory " + getBuildingByName("Factory").getIncomePerSecondBase() + " gold/s");
    }

    public int getTotalGoldPerSecond() {
        int total = 0;
        for (Building building : buildings) {
            total += building.getIncomePerSecond();
        }
        return total;
    }

    // Getter methods for buttons
    public GameButton getBuyMinerButton() {
        return buyMinerButton;
    }

    public GameButton getBuyBakeryButton() {
        return buyBakeryButton;
    }

    public GameButton getBuyFactoryButton() {
        return buyFactoryButton;
    }
}
