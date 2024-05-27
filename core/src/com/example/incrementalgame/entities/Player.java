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
    private Animation<TextureRegion> walkAnimation, attackAnimation, defeatAnimation;
    private float stateTime;
    private boolean isWalking;
    private boolean isAttacking;
    private boolean isDefeated;

    private float attackTimer = 0f;
    private float defeatTimer = 0f;
    private boolean isAttackReady = true;

    public Player(float x, float y, float width, float height, int health, int damage, ResourceManager resourceManager,
            Texture playerTexture, Animation<TextureRegion> walkAnimation, Animation<TextureRegion> attackAnimation, Animation<TextureRegion> defeatAnimation) {
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
        this.walkAnimation = walkAnimation;
        this.attackAnimation = attackAnimation;
        this.defeatAnimation = defeatAnimation;
        this.stateTime = 0f;
        this.defeatTimer = 0f;
        this.attackTimer = 0f;
        this.isWalking = true;
        this.isAttacking = false;
        this.isDefeated = false;
    }

     public void update(float deltaTime) {
    stateTime += deltaTime;
    if (isAttacking) {
        attackTimer += deltaTime;
        if (attackTimer >= 2f) {
            isAttackReady = true;
            attackTimer = 0f;
        }
    }
    if (isDefeated) {
        defeatTimer += deltaTime;
        if (defeatTimer >= 3f) {
            resetPlayer();
            defeatTimer = 0f;
        }
    } else if (isWalking) {
        moveRight(200 * deltaTime);
    }

    // Debugging output
    System.out.println("Player update: isDefeated=" + isDefeated + ", isWalking=" + isWalking + ", isAttacking=" + isAttacking);
}


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
    
    public TextureRegion getFrame() {
    if (isDefeated) {
        return defeatAnimation.getKeyFrame(stateTime, false);
    } else if (isAttacking) {
        return attackAnimation.getKeyFrame(stateTime, true);
    } else if (isWalking) {
        return walkAnimation.getKeyFrame(stateTime, true);
    } else {
        // Handle the case when none of the conditions are true
        System.err.println("Unexpected player state: not walking, attacking, or defeated. Defaulting to walk animation.");
        // Handle the case when none of the conditions are true
        return walkAnimation.getKeyFrame(stateTime, true); // Fallback to walking animation
    }
}

    public void triggerAttack() {
        if (isAttackReady) {
            isWalking = false;
            isAttacking = true;
            stateTime = 0f;
            isAttackReady = false;
        }
    }

    public void triggerDefeat() {
        isWalking = false;
        isAttacking = false;
        isDefeated = true;
        stateTime = 0f;
        defeatTimer = 0f;
    }

    //method to handle player defeat
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

    //method to reset the player
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

    //debugging output
    System.out.println("Player reset: isDefeated=" + isDefeated + ", isWalking=" + isWalking + ", isAttacking=" + isAttacking);
}



    public void resetAnims() {
        stateTime = 0f;
        isWalking = true;
        isAttacking = false;
        isDefeated = false;
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

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
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
