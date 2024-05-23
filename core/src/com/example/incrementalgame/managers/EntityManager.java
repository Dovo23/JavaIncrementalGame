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
    private WaveManager waveManager;
    private AgeManager ageManager;
    private PrestigeManager prestigeManager;

    public EntityManager(Assets assets, ResourceManager resourceManager, WaveManager waveManager) {
        this.assets = assets;
        this.resourceManager = resourceManager;
        this.waveManager = waveManager;
        // this.prestigeManager = prestigeManager;

        enemies = new Array<>();
        player = new Player(350, 20, 64, 64, 50, 3, resourceManager);
        // player.setResourceManager(resourceManager); //important to prevent a nullpointer exception
        // spawnEnemies(); 
        ageManager = new AgeManager(player);
        waveManager.initialize(this); // Initialize waveManager with this EntityManager
        
    }

    public void spawnEnemies(float healthMulti, float damageMulti, float expMulti) {

        Enemy enemy1 = new Enemy(700, 20, 64, 64, (int) (10 * healthMulti), (int) (1 * damageMulti));
        enemy1.setResourceManager(resourceManager); //important to prevent a nullpointer exception
        enemy1.setWaveManager(waveManager);
        enemies.add(enemy1);
        System.out.println("Enemy1HP:" + enemy1.getHealth() + " Enemy1Damage:" + enemy1.getDamage());

        Enemy enemy2 = new Enemy(600, 20, 64, 64, (int) (15 * healthMulti), (int) (2 * damageMulti));
        enemy2.setResourceManager(resourceManager); //important to prevent a nullpointer exception
        enemy2.setWaveManager(waveManager);
        enemies.add(enemy2);
        System.out.println("Enemy2HP:" + enemy2.getHealth() + " Enemy2Damage:" + enemy2.getDamage());

    }

    public void addEnemy(Enemy enemy) {
        enemy.setResourceManager(resourceManager);
        enemies.add(enemy);
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {  //automatic level up for testing
            resourceManager.addExp(500);
        }
        player.checkExpThreshold();
        ageManager.update(deltaTime);
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
        if (enemies.size == 0) {
            player.resetPlayerPos();
            waveManager.startNextWave();
        }
        if (player.getAge() >= 100) {
            // Trigger prestige if age reaches 100
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
            batch.draw(assets.enemyTexture, enemy.getBounds().x, enemy.getBounds().y, enemy.getBounds().width,
                    enemy.getBounds().height);
        }
        if (player != null) {
            batch.draw(assets.playerTexture, player.getBounds().x, player.getBounds().y, player.getBounds().width,
                    player.getBounds().height);
        }

        assets.font.draw(batch, "Level: " + player.getLevel(), 10, GameConfig.WORLD_HEIGHT - 140);
        assets.font.draw(batch, "Exp: " + (int) resourceManager.getExp() + "/" + (int) player.getNextLevelExp(), 10,
                GameConfig.WORLD_HEIGHT - 160);
        assets.font.draw(batch, "Title: " + player.getTitle(), 10, GameConfig.WORLD_HEIGHT - 180);
        assets.font.draw(batch, "Age: " + player.getAge(), 10, GameConfig.WORLD_HEIGHT - 200);
        assets.font.draw(batch, "Health: " + player.getCurrentHealth()+ "/" + player.getHealth(), 10, GameConfig.WORLD_HEIGHT - 220);
        assets.font.draw(batch, "Damage: " + player.getDamage(), 10, GameConfig.WORLD_HEIGHT - 240);
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
