package com.example.incrementalgame.entities;

import com.example.incrementalgame.Player.PlayerLevel;
import com.example.incrementalgame.managers.ResourceManager;

public class Player extends Entity {

    public boolean isAlive = true;
    private float initialX;
    private float initialY;
    private int initialHealth;
    private int initialDamage;
    private int baseHealth;
    private int baseDamage;
    private PlayerLevel playerLevel;
    // private ResourceManager resourceManager;
    
    public Player(float x, float y, float width, float height, int health, int damage, ResourceManager resourceManager) {
        super(x, y, width, height, health, damage);
        this.initialX = x;
        this.initialY = y;
        this.initialHealth = health;
        this.initialDamage = damage;
        this.baseHealth = health;
        this.baseDamage = damage;
        this.playerLevel = new PlayerLevel(resourceManager);

    }


    public void updateStats(float multiplier) {
        this.health = (int) (baseHealth * multiplier);
        this.damage = (int) (baseDamage * multiplier);
    }

    public void increaseBaseStats(int BaseDamage, int BaseHealth) {
        this.baseHealth = BaseHealth;
        this.baseDamage = BaseDamage;
    }

    public void checkExpThreshold() {
        playerLevel.checkExpThreshold(this);
    }

    public void moveLeft(float speed) {
        bounds.x -= speed;
    }

    public void moveRight(float speed) {
        bounds.x += speed;
    }

    protected void onDefeated() {
        super.onDefeated();
        isAlive = false;
        //death animation
        System.out.println("Player defeated!");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void resetPlayer() {
        if (isAlive == false) {
            health = initialHealth; // Reset health
            damage = initialDamage; // Reset damage both are more for the prestige and not death
            bounds.x = initialX; // Reset x position
            bounds.y = initialY; // Reset y position
            isAlive = true; // Revive player
        }
    }

    public String getTitle() {
        return playerLevel.getTitle();
    }

    public int getLevel() {
        return playerLevel.getLevel();
    }

    // public void setResourceManager(ResourceManager resourceManager) {
    //     this.resourceManager = resourceManager;
    // }

    // public void forcedLevelUp() {
    //     playerLevel.levelUp();
    // }

    public int getNextLevelExp() {
        return playerLevel.calculateNextLevelExp();
    }


}
