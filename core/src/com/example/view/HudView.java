package com.example.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.managers.BuildingManager;
import com.example.incrementalgame.managers.PrestigeManager;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;
import com.example.incrementalgame.config.GameConfig;

public class HudView {
    private Assets assets;
    private ResourceManager resourceManager;
    private BuildingManager buildingManager;
    private PrestigeManager prestigeManager;
    private WaveManager waveManager;

    public HudView(Assets assets, ResourceManager resourceManager, BuildingManager buildingManager,
            PrestigeManager prestigeManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.buildingManager = buildingManager;
        this.prestigeManager = prestigeManager;
        this.waveManager = waveManager;
    }

    //renders the HUD
    public void render(SpriteBatch batch) {
        assets.font.draw(batch, "Gold: " + resourceManager.getGold(), 10, GameConfig.WORLD_HEIGHT - 20);
        assets.font.draw(batch, buildingManager.getTotalGoldPerSecond() + " gold/s", 10, GameConfig.WORLD_HEIGHT - 40);
        assets.font.draw(batch, "Prestige Level: " + prestigeManager.getPrestigeLevel(), 10, GameConfig.WORLD_HEIGHT - 60);
        assets.font.draw(batch, "Required gold till next prestige: " + (int) prestigeManager.getNextPrestigeRequirement(), 10, GameConfig.WORLD_HEIGHT - 80);
        assets.font.draw(batch, "Wave: " + (int) waveManager.getWaveNumber(), 10, GameConfig.WORLD_HEIGHT - 100);
    }
}
