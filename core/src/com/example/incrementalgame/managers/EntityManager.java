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

    public EntityManager(Assets assets, ResourceManager resourceManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.waveManager = waveManager;
        enemies = new Array<>();
        player = new Player(350, 20, 64, 64, 50, 3, resourceManager, assets.playerTexture);
        ageManager = new AgeManager(player);
        waveManager.initialize(this);
    }

    public void spawnEnemies(float healthMulti, float damageMulti, float expMulti) {
        Enemy enemy1 = new Enemy(700, 20, 64, 64, (int) (10 * healthMulti), (int) (1 * damageMulti));
        enemy1.setResourceManager(resourceManager);
        enemy1.setWaveManager(waveManager);
        enemies.add(enemy1);
        System.out.println("Enemy1HP:" + enemy1.getHealth() + " Enemy1Damage:" + enemy1.getDamage());

        Enemy enemy2 = new Enemy(600, 20, 64, 64, (int) (15 * healthMulti), (int) (2 * damageMulti));
        enemy2.setResourceManager(resourceManager);
        enemy2.setWaveManager(waveManager);
        enemies.add(enemy2);
        System.out.println("Enemy2HP:" + enemy2.getHealth() + " Enemy2Damage:" + enemy2.getDamage());
    }

    public void addEnemy(Enemy enemy) {
        enemy.setResourceManager(resourceManager);
        enemies.add(enemy);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            resourceManager.addExp(500);
        }
        player.checkExpThreshold();
        ageManager.update(deltaTime);
        // player.update(deltaTime);
        movement();
        checkCollisions();

        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).isDefeated()) {
                enemies.removeIndex(i);
            }
        }
        if (player.isDefeated()) {
            player.resetPlayer();
            enemies.clear();
        }
        if (enemies.size == 0) {
            player.resetPlayerPos();
            waveManager.startNextWave();
        }
        if (player.getAge() >= 100) {
            prestigeManager.performPrestige();
            System.out.println("Current expMulti: " + resourceManager.getExpMulti());
        }
    }

    private void checkCollisions() {
        if (player != null) {
            for (Enemy enemy : enemies) {
                if (player.getBounds().overlaps(enemy.getBounds())) {
                    player.takeDamage(enemy.getDamage());
                    enemy.takeDamage(player.getDamage());
                    System.out.println("Player health: " + player.getCurrentHealth() + " Enemy health: " + enemy.getHealth() + " Enemy damage: " + enemy.getDamage() + " Player damage: " + player.getDamage());
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
