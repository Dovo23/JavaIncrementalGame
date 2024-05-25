package com.example.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Building;
import com.example.incrementalgame.entities.GameButton;
import com.example.incrementalgame.managers.BuildingManager;

public class BuildingView {
    private Assets assets;

    public BuildingView(Assets assets) {
        this.assets = assets;
    }

    public void render(SpriteBatch batch, BuildingManager buildingManager) {
        BitmapFont font = assets.font;
        GameButton buyMinerButton = buildingManager.getBuyMinerButton();
        GameButton buyBakeryButton = buildingManager.getBuyBakeryButton();
        GameButton buyFactoryButton = buildingManager.getBuyFactoryButton();

        // Render buttons
        buyMinerButton.draw(batch, assets.font, assets.buttonTexture);
        buyBakeryButton.draw(batch, assets.font, assets.buttonTexture);
        buyFactoryButton.draw(batch, assets.font, assets.buttonTexture);

        // Render building costs next to their respective buttons
        font.draw(batch, "Miner Cost: " + buildingManager.getBuildingByName("Miner").getCost(), buyMinerButton.getBounds().x, buyMinerButton.getBounds().y + buyMinerButton.getBounds().height + 20);
        font.draw(batch, "Bakery Cost: " + buildingManager.getBuildingByName("Bakery").getCost(), buyBakeryButton.getBounds().x, buyBakeryButton.getBounds().y + buyBakeryButton.getBounds().height + 20);
        font.draw(batch, "Factory Cost: " + buildingManager.getBuildingByName("Factory").getCost(), buyFactoryButton.getBounds().x, buyFactoryButton.getBounds().y + buyFactoryButton.getBounds().height + 20);
    }
}
