// Player.java
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
    //private Texture playerTexture;
    private Animation<TextureRegion> walkAnimation, attackAnimation, defeatAnimation, idleAnimation;
    private float stateTime;
    private boolean isWalking;
    private boolean isAttacking;
    private boolean isDefeated;
    private float attackTimer = 0f;
    private float defeatTimer = 0f;
    private boolean isAttackReady = true;
    private float attackCooldown = 1.5f; 
    public Player(float x, float y, float width, float height, int health, int damage, ResourceManager resourceManager,
                  Texture playerTexture, Animation<TextureRegion> walkAnimation, Animation<TextureRegion> attackAnimation, Animation<TextureRegion> defeatAnimation, Animation<TextureRegion> idleAnimation) {
        super(x, y, width, height, health, damage);
        this.initialX = x;
        this.initialY = y;
        this.initialHealth = health;
        this.initialDamage = damage;
        this.baseHealth = health;
        this.baseDamage = damage;
        this.age = 90;
        this.playerLevel = new PlayerLevel(resourceManager);
        //this.playerTexture = playerTexture;
        this.walkAnimation = walkAnimation;
        this.attackAnimation = attackAnimation;
        this.defeatAnimation = defeatAnimation;
        this.idleAnimation = idleAnimation;
        this.stateTime = 0f;
        this.defeatTimer = 0f;
        this.attackTimer = 0f;
        this.isWalking = true;
        this.isAttacking = false;
        this.isDefeated = false;

        System.out.println("Initialized Player with attack animation duration: " + attackAnimation.getAnimationDuration());
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        // Update attack timer and check if attack is ready
        if (isAttacking) {
            attackTimer += deltaTime;
            System.out.println("isAttacking loop reached! Attack Timer: " + attackTimer + " Attack Animation Duration: " + attackAnimation.getAnimationDuration());

            if (attackTimer >= attackAnimation.getAnimationDuration()) {
                isAttacking = false; 
                isAttackReady = false;
                attackTimer = 0f;
                System.out.println("Attack finished: isAttacking set to false, isAttackReady set to false" + " isAttacking: " + isAttacking + ", isAttackReady: " + isAttackReady);
            }
        } else {

            if (!isAttackReady) {
                attackTimer += deltaTime;
                if (attackTimer >= attackCooldown) {
                    isAttackReady = true;
                    attackTimer = 0f;
                    System.out.println("Cooldown finished: isAttackReady set to true");
                }
            }
        }

        System.out.println("Player attack timer= " + attackTimer + ", isAttackReady=" + isAttackReady);

        // Update defeat timer
        if (isDefeated) {
            defeatTimer += deltaTime;
            if (defeatTimer >= 3f) {
                resetPlayer();
                defeatTimer = 0f;
            }
        } else if (isWalking) {
            moveRight(200 * deltaTime);
        }

        System.out.println("Player update: isDefeated=" + isDefeated + ", isWalking=" + isWalking + ", isAttacking=" + isAttacking);
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

    public TextureRegion getFrame() {
        if (isDefeated) {
            return defeatAnimation.getKeyFrame(stateTime, false);
        } else if (isAttacking) {
            return attackAnimation.getKeyFrame(stateTime, false); // Play attack animation only once
        } else if (isWalking) {
            return walkAnimation.getKeyFrame(stateTime, true);
        } else {
            return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void triggerAttack() {
        if (isAttackReady) {
            isWalking = false;
            isAttacking = true;
            stateTime = 0f;
            isAttackReady = false;
            attackTimer = 0f; 
            System.out.println("Player attack triggered as intended! isAttacking: " + isAttacking + ", attackTimer: " + attackTimer);
        } else {
            System.out.println("Player attack not ready! isAttacking: " + isAttacking + ", isAttackReady: " + isAttackReady);
        }
    }

    public void triggerDefeat() {
        isWalking = false;
        isAttacking = false;
        isDefeated = true;
        stateTime = 0f;
        defeatTimer = 0f;
    }

    @Override
    protected void onDefeated() {
        super.onDefeated();
        isAlive = false;
        triggerDefeat();
        System.out.println("Player defeated!");
    }

    @Override
    public void takeDamage(int damage) {
        if (!isDefeated) {
            super.takeDamage(damage);
        }
    }

    public boolean isAlive() {
        return !isDefeated;
    }

    public boolean isAttackReady() {
        return isAttackReady;
    }

    public void resetPlayer() {
        this.currentHealth = health;
        resetPlayerPos();
        isDefeated = false;
        setDefeated(false);
        isWalking = true;
        isAttacking = false;
        isAttackReady = true;
        stateTime = 0f;
        isAlive = true;

        System.out.println("Player reset: isDefeated=" + isDefeated + ", isWalking=" + isWalking + ", isAttacking=" + isAttacking);
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
