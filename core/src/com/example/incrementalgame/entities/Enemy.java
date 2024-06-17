package com.example.incrementalgame.entities;

import java.util.ArrayList;
import java.util.List;

//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.incrementalgame.interfaces.Loot;
import com.example.incrementalgame.loot.GoldLoot;
import com.example.incrementalgame.loot.Exp;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;

public class Enemy extends Entity {
    private ResourceManager resourceManager;
    private WaveManager waveManager;
    private Animation<TextureRegion> attackAnimation, defeatAnimation, idleAnimation;
    private float stateTime;
    private boolean isAttacking;
    private boolean isDefeated = false;
    private float attackTimer = 0f;
    private float defeatTimer = 0f;
    private boolean isAttackReady = true;
    private float attackCooldown = 1.5f; 

    public Enemy(float x, float y, float width, float height, int health, int damage, Animation<TextureRegion> attackAnimation, Animation<TextureRegion> defeatAnimation, Animation<TextureRegion> idleAnimation) {
        super(x, y, width, height, health, damage);
        this.attackAnimation = attackAnimation;
        this.defeatAnimation = defeatAnimation;
        this.idleAnimation = idleAnimation;
        this.stateTime = 0f;
        this.isAttacking = false;
        this.isDefeated = false;
        this.isAttackReady = true;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
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
        System.out.println("Enemy attack timer= " + attackTimer + ", isAttackReady=" + isAttackReady);

        if (isDefeated) {
            defeatTimer += deltaTime;
        }

        System.out.println("Enemy update: isDefeated=" + isDefeated + ", isAttacking=" + isAttacking);
    }

    public void triggerAttack() {
        if (isAttackReady) {
            isAttacking = true;
            isAttackReady = false;
            attackTimer = 0f;
            System.out.println("Enemy attack triggered!");
        }
    }

    public void triggerDefeat() {
        isAttacking = false;
        isDefeated = true;
        stateTime = 0f;
        defeatTimer = 0f;
    }

    @Override
    public void takeDamage(int damage) {
        if (!isDefeated) {
            super.takeDamage(damage);
        }
    }

    public boolean isAttackReady() {
        return isAttackReady;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public List<Loot> dropLoot() {
        List<Loot> drops = new ArrayList<>();
        float expMultiplier = waveManager.getExpMulti();
        drops.add(new GoldLoot(5000)); // Currently hardcoded
        drops.add(new Exp((int) (20 * expMultiplier)));  // Currently hardcoded
        return drops;
    }

    @Override
    protected void onDefeated() {
        super.onDefeated();
        triggerDefeat();
        if (resourceManager != null) {
            List<Loot> loots = dropLoot();
            for (Loot loot : loots) {
                loot.apply(resourceManager);
            }
            System.out.println("Enemy defeated!");
        } else {
            System.out.println("Resource Manager is null");
        }
        //isDefeated = false;
    }

    public TextureRegion getFrame() {
        if (isDefeated) {
            return defeatAnimation.getKeyFrame(stateTime, false);
        } else if (isAttacking) {
            return attackAnimation.getKeyFrame(stateTime, false); 
        } else {
            return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    public boolean shouldBeRemoved() {
        return isDefeated && defeatTimer >= 3f;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void setWaveManager(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    
}
