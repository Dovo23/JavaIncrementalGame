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
    private int age;
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
        this.age = 90;
        this.playerLevel = new PlayerLevel(resourceManager);

    }


    public void updateStats(float multiplier) {
        this.health = (int) (baseHealth * multiplier);
        this.damage = (int) (baseDamage * multiplier);
        this.currentHealth = health;  
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
        resetPlayerPos();
        this.currentHealth = health;
        //death animation
        System.out.println("Player defeated!");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void resetPlayer() {
        if (isAlive == false) {
            this.currentHealth = health;
            resetPlayerPos();
            isAlive = true; // Revive player
            System.out.println("Player got reset!");
        }
    }

    public void resetPlayerPos() {
        bounds.x = initialX; // Reset x position
        bounds.y = initialY; // Reset y position
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

    public int getAge() {
        return age;
    }

    public void addAge() {
        age++;
    }

    public void resetAge() {
        age = 15;
    }

    public void resetStats() {
        this.baseHealth = initialHealth;
        this.baseDamage = initialDamage;
        this.health = baseHealth;
        this.damage = baseDamage;
        this.currentHealth = health; 
        playerLevel.resetLevel();
        playerLevel.resetTitle();
        playerLevel.resetExp();
    }


}
