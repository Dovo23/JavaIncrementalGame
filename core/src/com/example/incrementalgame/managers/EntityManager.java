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
        player = new Player(-50, -30, 256, 256, 50, 3, resourceManager, assets.playerTexture, assets.walkAnimation, assets.attackAnimation, assets.defeatAnimation);
        ageManager = new AgeManager(player);
        waveManager.initialize(this);
    }

    //method to spawn enemies with different stats based on the wave multiplier
    public void spawnEnemies(float healthMulti, float damageMulti, float expMulti) {
        Enemy enemy1 = new Enemy(700, 60, 64, 64, (int) (500 * healthMulti), (int) (2 * damageMulti));
        enemy1.setResourceManager(resourceManager);
        enemy1.setWaveManager(waveManager);
        enemies.add(enemy1);
        System.out.println("Enemy1HP:" + enemy1.getHealth() + " Enemy1Damage:" + enemy1.getDamage());

        // Enemy enemy2 = new Enemy(600, 60, 64, 64, (int) (15 * healthMulti), (int) (2 * damageMulti));
        // enemy2.setResourceManager(resourceManager);
        // enemy2.setWaveManager(waveManager);
        // enemies.add(enemy2);
        // System.out.println("Enemy2HP:" + enemy2.getHealth() + " Enemy2Damage:" + enemy2.getDamage());
    }

    //method to add an enemy to the array of enemies
    public void addEnemy(Enemy enemy) {
        enemy.setResourceManager(resourceManager);
        enemies.add(enemy);
    }

    //methods to update game state
    public void update(float deltaTime) {
        //ability to add exp for testing purposes
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            resourceManager.addExp(500);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            waveManager.setWave(waveManager.getWaveNumber() + 1);;
        }
        player.checkExpThreshold();
        ageManager.update(deltaTime);
        player.update(deltaTime);
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
        checkCollisions();

        //removing defeated enemies from the array
        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).isDefeated()) {
                enemies.removeIndex(i);
            }
        }
        if (player.isDefeated() && !player.isAlive()) {
            //wait for the player to reset after 5 seconds in the Player class
            return;
        }
        //starting the next wave if all enemies are defeated
        if (enemies.size == 0 && !player.isDefeated()) {
            player.resetPlayerPos();
            player.resetPlayer();
            waveManager.startNextWave();
        }
        //prestige once the player reaches 100 years old
        if (player.getAge() >= 100) {
            prestigeManager.performPrestige();
            System.out.println("Current expMulti: " + resourceManager.getExpMulti());
        }
    }

    //method to check for collisions between player and enemies with LibGDX 
     private void checkCollisions() {
        if (player != null) {
            for (Enemy enemy : enemies) {
                if (player.getBounds().overlaps(enemy.getBounds())) {
                    System.out.println("Collision detected! Player Status: " + player.isDefeated());
                    if (!player.isDefeated()) {
                        player.triggerAttack();
                        //player.takeDamage(enemy.getDamage());
                        enemy.takeDamage(player.getDamage());
                        System.out.println("Player health: " + player.getCurrentHealth() + " Enemy health: " + enemy.getHealth() + " Enemy damage: " + enemy.getDamage() + " Player damage: " + player.getDamage());
                    }

                    // Enemy attacks player
                    if (!enemy.isDefeated() && enemy.isAttackReady()) {
                        enemy.triggerAttack();
                        player.takeDamage(enemy.getDamage());
                        System.out.println("Enemy attacks! Player health: " + player.getCurrentHealth() + " Enemy health: " + enemy.getCurrentHealth());
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

     //setting reference to the prestige manager
    public void initialize(PrestigeManager prestigeManager) {
        this.prestigeManager = prestigeManager;
    }
}
