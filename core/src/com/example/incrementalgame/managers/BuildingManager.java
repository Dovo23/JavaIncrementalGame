package com.example.incrementalgame.managers;

import com.badlogic.gdx.utils.Array;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Building;
import com.example.incrementalgame.entities.GameButton;

public class BuildingManager {
    private Array<Building> buildings;
    private GameButton buyMinerButton, buyBakeryButton, buyFactoryButton, buyKingdomButton;
    private Assets assets;
    private ResourceManager resourceManager;
    private EntityManager entityManager;

    public BuildingManager(Assets assets, ResourceManager resourceManager, EntityManager entityManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.entityManager = entityManager;


        buildings = new Array<>();
        addBuildings();
        addButtons();
    }

    //method to add buildings to array
    private void addBuildings() {
        buildings.add(new Building("Wheat Field", 10, 1));
        buildings.add(new Building("Farm", 100, 10));
        buildings.add(new Building("Factory", 500, 100));
        buildings.add(new Building("Kingdom", 100, 10000));
    }

    //method to add buttons
    private void addButtons() {
        buyMinerButton = new GameButton(650, 360, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Wheat Field " + getBuildingByName("Wheat Field").getIncomePerSecondBase() + " gold/s");
        buyBakeryButton = new GameButton(650, 290, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Bakery " + getBuildingByName("Farm").getIncomePerSecondBase() + " gold/s");
        buyFactoryButton = new GameButton(650, 220, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Factory " + getBuildingByName("Factory").getIncomePerSecondBase() + " gold/s");
        buyKingdomButton = new GameButton(450, 360, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Kingdom " + getBuildingByName("Kingdom").getIncomePerSecondBase() + " gold/s");
    }

    public void update(float delta) {
        updateButtonLabels();
    }
    
    //method to handle the purchase of a building
    public void buyBuilding(String buildingName) {
        Building building = getBuildingByName(buildingName);
        if (building != null && resourceManager.getGold() >= building.getCost()) {
            resourceManager.subtractGold(building.getCost());
            building.increaseLevel();
            if ("Kingdom".equals(buildingName)) {
                System.out.println("You have won!"); //temporary, should be a pop up or something
            }
        }
    }

    //method to get a building by name
    public Building getBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    //method to handle button input
    public void handleButtonInput(float x, float y) {
        if (buyMinerButton.getBounds().contains(x, y)) {
            buyBuilding("Wheat Field");
        } else if (buyBakeryButton.getBounds().contains(x, y)) {
            buyBuilding("Farm");
        } else if (buyFactoryButton.getBounds().contains(x, y)) {
            buyBuilding("Factory");
        } else if (buyKingdomButton.getBounds().contains(x, y) && isKingdomPurchaseable()) {
             buyBuilding("Kingdom");
        }
    }
    
    //method to update button labels on the purchase buttons
    private void updateButtonLabels() {
        buyMinerButton.setText("Buy Wheat Field " + getBuildingByName("Wheat Field").getIncomePerSecondBase() + " gold/s");
        buyBakeryButton.setText("Buy Farm " + getBuildingByName("Farm").getIncomePerSecondBase() + " gold/s");
        buyFactoryButton.setText("Buy Factory " + getBuildingByName("Factory").getIncomePerSecondBase() + " gold/s");
        if (isKingdomPurchaseable()) {
            buyKingdomButton.setText("Buy Kingdom " + getBuildingByName("Kingdom").getIncomePerSecondBase() + " gold/s");
        }
    }
    
     //check if Kingdom building can be purchased
    public boolean isKingdomPurchaseable() {
        return resourceManager.getGold() >= 1000 && "Peasant".equals(entityManager.getPlayer().getTitle());  //temporary values for testing
    }

    //method to get the total gold per second to display in the hud
    public int getTotalGoldPerSecond() {
        int total = 0;
        for (Building building : buildings) {
            total += building.getIncomePerSecond();
        }
        return total;
    }

    public GameButton getBuyMinerButton() {
        return buyMinerButton;
    }

    public GameButton getBuyBakeryButton() {
        return buyBakeryButton;
    }

    public GameButton getBuyFactoryButton() {
        return buyFactoryButton;
    }

    public GameButton getBuyKingdomButton() {
        return buyKingdomButton;
    }
}
