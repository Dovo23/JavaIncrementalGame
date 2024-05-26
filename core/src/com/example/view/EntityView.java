package com.example.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.entities.Enemy;
import com.example.incrementalgame.entities.Player;
import com.example.incrementalgame.managers.EntityManager;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.config.GameConfig;

public class EntityView {
    private Assets assets;

    public EntityView(Assets assets) {
        this.assets = assets;
    }

    public void render(SpriteBatch batch, EntityManager entityManager, ResourceManager resourceManager) {
        for (Enemy enemy : entityManager.getEnemies()) {
            batch.draw(assets.enemyTexture, enemy.getBounds().x, enemy.getBounds().y, enemy.getBounds().width, enemy.getBounds().height);
        }
        Player player = entityManager.getPlayer();
        if (player != null) {
            batch.draw(player.getFrame(), player.getBounds().x, player.getBounds().y , player.getBounds().width, player.getBounds().height);
            assets.font.draw(batch, "Level: " + player.getLevel(), 10, GameConfig.WORLD_HEIGHT - 140);
            assets.font.draw(batch, "Exp: " + (int) resourceManager.getExp() + "/" + (int) player.getNextLevelExp(), 10, GameConfig.WORLD_HEIGHT - 160);
            assets.font.draw(batch, "Title: " + player.getTitle(), 10, GameConfig.WORLD_HEIGHT - 180);
            assets.font.draw(batch, "Age: " + player.getAge(), 10, GameConfig.WORLD_HEIGHT - 200);
            assets.font.draw(batch, "Health: " + player.getCurrentHealth() + "/" + player.getHealth(), 10, GameConfig.WORLD_HEIGHT - 220);
            assets.font.draw(batch, "Damage: " + player.getDamage(), 10, GameConfig.WORLD_HEIGHT - 240);
        }
    }
}
