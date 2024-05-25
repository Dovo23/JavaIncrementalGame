package com.example.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.managers.PrestigeManager;

public class PrestigeView {
    private Assets assets;

    public PrestigeView(Assets assets) {
        this.assets = assets;
    }

    public void render(SpriteBatch batch, PrestigeManager prestigeManager) {
        // Render the prestige button if the requirement is met
        if (prestigeManager.isPrestigeAvailable()) {
            prestigeManager.getPrestigeButton().draw(batch, assets.font, assets.buttonTexture);
        }
    }
}
