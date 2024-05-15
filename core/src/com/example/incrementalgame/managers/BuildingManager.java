package com.example.incrementalgame.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Building;
import com.example.incrementalgame.entities.GameButton;

public class BuildingManager {
    public Building miner, bakery, factory;
    private GameButton buyMinerButton, buyBakeryButton, buyFactoryButton;
    private Assets assets;

    public BuildingManager(Assets assets) {
        this.assets = assets;
        addBuildings();
        addButtons();
    }

    private void addBuildings() {
        // Create and add buildings to the array
        miner = new Building("Miner", 10, 1);
        bakery = new Building("Bakery", 100, 10);
        factory = new Building("Factory", 500, 100);
    }

    private void addButtons() {
        buyMinerButton = new GameButton(650, 360, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Miner " + miner.getIncomePerSecondBase() + " gold/s");
        buyBakeryButton = new GameButton(650, 290, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Bakery " + bakery.getIncomePerSecondBase() + " gold/s");
        buyFactoryButton = new GameButton(650, 220, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Buy Factory " + factory.getIncomePerSecondBase() + " gold/s");
    }

    public void update(float delta) {
       updateButtonLabels();
    }

    public void handleButtonInput(float x, float y) {
        if (buyMinerButton.getBounds().contains(x, y)) {
            miner.buy();
        } else if (buyBakeryButton.getBounds().contains(x, y)) {
            bakery.buy();
        } else if (buyFactoryButton.getBounds().contains(x, y)) {
            factory.buy();
        }
    }
     
    private void updateButtonLabels() {
        buyMinerButton.setText("Buy Miner " + miner.getIncomePerSecondBase() + " gold/s");
        buyBakeryButton.setText("Buy Bakery " + bakery.getIncomePerSecondBase() + " gold/s");
        buyFactoryButton.setText("Buy Factory " + factory.getIncomePerSecondBase() + " gold/s");
    }

    public void render(SpriteBatch batch) {
        BitmapFont font = assets.font;

        // Render buttons
        buyMinerButton.draw(batch, assets.font, assets.buttonTexture);
        buyBakeryButton.draw(batch, assets.font, assets.buttonTexture);
        buyFactoryButton.draw(batch, assets.font, assets.buttonTexture);

        // Render building costs next to their respective buttons
        font.draw(batch, "Miner Cost: " + miner.getCost(), buyMinerButton.getBounds().x, buyMinerButton.getBounds().y + buyMinerButton.getBounds().height + 20);
        font.draw(batch, "Bakery Cost: " + bakery.getCost(), buyBakeryButton.getBounds().x, buyBakeryButton.getBounds().y + buyBakeryButton.getBounds().height + 20);
        font.draw(batch, "Factory Cost: " + factory.getCost(), buyFactoryButton.getBounds().x,buyFactoryButton.getBounds().y + buyFactoryButton.getBounds().height + 20);
    }
    
}
