package com.example.incrementalgame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.example.incrementalgame.assets.Assets;
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
        // Initialize player and enemies
        player = new Player(350, 20, 64, 64, 1000, 10);
        spawnEnemies(); //can spawn depending on certain factors
    }

    private void spawnEnemies() {
        // Spawn enemies at game start or based on some conditions
        enemies.add(new Enemy(700, 20, 64, 64, 100, 5));
        enemies.add(new Enemy(600, 20, 64, 64, 120, 7));
    }

    public void update(float deltaTime) {

        // for (Enemy enemy : enemies) {
        //     if (enemy.isDefeated()) {
        //         enemies.removeValue(enemy, true);
        //     }
        // }
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
                    System.out.println("Player health: " + player.getHealth() + " Enemy health: " + enemy.getHealth());
                }
            }
        }
}
    
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
            batch.draw(assets.playerTexture, player.getBounds().x, player.getBounds().y, player.getBounds().width, player.getBounds().height);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
