// EntityManager.java
package com.example.incrementalgame.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private WaveManager waveManager;
    private AgeManager ageManager;
    private PrestigeManager prestigeManager;
    //private SoundManager soundManager;

    public EntityManager(Assets assets, ResourceManager resourceManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.waveManager = waveManager;
        //this.soundManager = soundManager;
        enemies = new Array<>();
        player = new Player(-50, -30, 256, 256, 50, 3, resourceManager, assets.playerTexture, assets.walkAnimation, assets.attackAnimation, assets.defeatAnimation, assets.idleAnimation);
        ageManager = new AgeManager(player);
        waveManager.initialize(this);
    }

    public void spawnEnemies(float healthMulti, float damageMulti, float expMulti) {
        Enemy enemy1 = new Enemy(700, 60, 64, 64, (int) (5 * healthMulti), (int) (2 * damageMulti), assets.wormAttackAnimation, assets.wormDefeatAnimation, assets.wormIdleAnimation);
        enemy1.setResourceManager(resourceManager);
        enemy1.setWaveManager(waveManager);
        enemies.add(enemy1);
    }

    public void addEnemy(Enemy enemy) {
        enemy.setResourceManager(resourceManager);
        enemies.add(enemy);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            resourceManager.addExp(500);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            waveManager.setWave(waveManager.getWaveNumber() + 1);
        }
        player.checkExpThreshold();
        ageManager.update(deltaTime);
        player.update(deltaTime);
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
        checkCollisions();

        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).shouldBeRemoved()) {
                enemies.removeIndex(i);
                System.out.println("Enemy removed!");
            }
        }
        if (player.isDefeated() && !player.isAlive()) {
            return;
        }
        if (enemies.size == 0 && !player.isDefeated()) {
            player.resetPlayerPos();
            player.resetPlayer();
            waveManager.startNextWave();
            System.out.println("New Wave: " + waveManager.getWaveNumber());
        }
        if (player.getAge() >= 100) {
            prestigeManager.performPrestige();
            System.out.println("Current expMulti: " + resourceManager.getExpMulti());
        }
    }

    private void checkCollisions() {
        if (player != null) {
            for (Enemy enemy : enemies) {
                if (player.getBounds().overlaps(enemy.getBounds()) && !player.isDefeated() && !enemy.isDefeated()) {
                    System.out.println("Collision detected!");

                    if (!player.isDefeated() && !enemy.isDefeated()) {
                        if (player.isAttackReady()) {
                            System.out.println("Player isAttackReady() = " + player.isAttackReady());
                            player.triggerAttack();
                            assets.playPlayerAttackSound(); 
                            enemy.takeDamage(player.getDamage());
                            System.out.println("Player attack triggered as intended!");
                            System.out.println("Player health: " + player.getCurrentHealth() + ", Enemy health: " + enemy.getHealth() + ", Enemy damage: " + enemy.getDamage() + ", Player damage: " + player.getDamage());
                        }

                        if (enemy.isAttackReady()) {
                            System.out.println("Enemy isAttackReady() = " + enemy.isAttackReady());
                            enemy.triggerAttack();
                            assets.playPlayerHitSound(); 
                            player.takeDamage(enemy.getDamage());
                            System.out.println("Enemy attack triggered as intended!");
                            System.out.println("Enemy attacks! Player health: " + player.getCurrentHealth() + ", Enemy health: " + enemy.getCurrentHealth());
                        }
                    }
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

    public Player getPlayer() {
        return player;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public void initialize(PrestigeManager prestigeManager) {
        this.prestigeManager = prestigeManager;
    }
}
