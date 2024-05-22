package com.example.incrementalgame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Enemy;
import com.example.incrementalgame.entities.Player;

public class EntityManager {
    private Array<Enemy> enemies;
    private Player player;
    private Assets assets;
    private float speed = 200;
    private ResourceManager resourceManager;

    public EntityManager(Assets assets, ResourceManager resourceManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        
        enemies = new Array<>();
        player = new Player(350, 20, 64, 64, 1000, 10, resourceManager);
        // player.setResourceManager(resourceManager); //important to prevent a nullpointer exception
        spawnEnemies(); //can spawn depending on certain factors
    }

    private void spawnEnemies() {

        Enemy enemy1 = new Enemy(700, 20, 64, 64, 100, 5);
        enemy1.setResourceManager(resourceManager);  //important to prevent a nullpointer exception
        enemies.add(enemy1);

        Enemy enemy2 = new Enemy(600, 20, 64, 64, 120, 7);
        enemy2.setResourceManager(resourceManager);  //important to prevent a nullpointer exception
        enemies.add(enemy2);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            resourceManager.addExp(100);
        }
             player.checkExpThreshold();
             //System.out.println("Checking EXP Threshold - Current EXP: " + resourceManager.getExp());
             //System.out.println("next exp: " + player.getNextLevelExp() );
        
        
        movement(); 
        checkCollisions();
        
        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).isDefeated()) {
                enemies.removeIndex(i); // Remove defeated enemies with libGDX Array method
            }
        }
        if (player.isDefeated()) {
            player.resetPlayer();
        }

    }

    private void checkCollisions() {
        if (player != null) {
            for (Enemy enemy : enemies) {
                if (player.getBounds().overlaps(enemy.getBounds())) {
                    player.takeDamage(enemy.getDamage());
                    enemy.takeDamage(player.getDamage());
                    //System.out.println("Player health: " + player.getHealth() + " Enemy health: " + enemy.getHealth() + " Enemy damage: " + enemy.getDamage() + " Player damage: " + player.getDamage());
                }
            }
        }
}
    //this should be in the player class lol
    public void movement() {
        if (player != null && player.isAlive()) {
             if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                 player.moveLeft(speed * Gdx.graphics.getDeltaTime());
                 
             }
             if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                 player.moveRight(speed * Gdx.graphics.getDeltaTime());
             }
         }
    }

    
    public void render(SpriteBatch batch) {
        
        for (Enemy enemy : enemies) {
            batch.draw(assets.enemyTexture, enemy.getBounds().x, enemy.getBounds().y, enemy.getBounds().width, enemy.getBounds().height);
        }
        if (player != null) {
            batch.draw(assets.playerTexture, player.getBounds().x, player.getBounds().y, player.getBounds().width,
                    player.getBounds().height);
        }
        
        assets.font.draw(batch, "Level: " + player.getLevel(), 10, GameConfig.WORLD_HEIGHT - 140);
        assets.font.draw(batch, "Exp: " + (int) resourceManager.getExp() + "/" + (int) player.getNextLevelExp(), 10, GameConfig.WORLD_HEIGHT - 160);
        assets.font.draw(batch, "Title: " + player.getTitle(), 10, GameConfig.WORLD_HEIGHT - 180);
        assets.font.draw(batch, "Health: " + player.getHealth(), 10, GameConfig.WORLD_HEIGHT - 200);
        assets.font.draw(batch, "Damage: " + player.getDamage(), 10, GameConfig.WORLD_HEIGHT - 220);
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }


    
}
