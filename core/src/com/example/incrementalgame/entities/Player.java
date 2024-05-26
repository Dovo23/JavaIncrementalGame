package com.example.incrementalgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Texture playerTexture;

    public Player(float x, float y, float width, float height, int health, int damage, ResourceManager resourceManager,
            Texture playerTexture) {
        super(x, y, width, height, health, damage);
        this.initialX = x;
        this.initialY = y;
        this.initialHealth = health;
        this.initialDamage = damage;
        this.baseHealth = health;
        this.baseDamage = damage;
        this.age = 90;
        this.playerLevel = new PlayerLevel(resourceManager);
        this.playerTexture = playerTexture;
    }

    // public void update(float deltaTime) {
    //     //not necessary atm but will be later
    // }
    //method to update player stats based on multiplier
    public void updateStats(float multiplier) {
        this.health = (int) (baseHealth * multiplier);
        this.damage = (int) (baseDamage * multiplier);
        this.currentHealth = health;
    }

    //method to increase the base stats of the player through the title system
    public void increaseBaseStats(int BaseDamage, int BaseHealth) {
        this.baseHealth = BaseHealth;
        this.baseDamage = BaseDamage;
    }

    //was used to test stuff
    public void checkExpThreshold() {
        playerLevel.checkExpThreshold(this);
    }

    //movement methods
    public void moveLeft(float speed) {
        bounds.x -= speed;
    }

    public void moveRight(float speed) {
        bounds.x += speed;
    }

    //method to handle player defeat
    protected void onDefeated() {
        super.onDefeated(); //will be used for future implementations, currently has no use
        isAlive = false; //setting player status to dead
        System.out.println("Player defeated!");
    }

    public boolean isAlive() {
        return isAlive;
    }
    //method to reset the player
    public void resetPlayer() {
        if (!isAlive) {
            this.currentHealth = health;
            resetPlayerPos();
            isAlive = true;
            System.out.println("Player got reset!");
        }
    }

    public void resetPlayerPos() {
        bounds.x = initialX;
        bounds.y = initialY;
    }

    public String getTitle() {
        return playerLevel.getTitle();
    }

    public int getLevel() {
        return playerLevel.getLevel();
    }

    public int getNextLevelExp() {
        return playerLevel.calculateNextLevelExp();
    }

    public int getAge() {
        return age;
    }

    public void addAge() {
        age++;
    }

    //reset methods used for prestige
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
