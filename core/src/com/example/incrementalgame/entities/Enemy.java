package com.example.incrementalgame.entities;

import java.util.ArrayList;
import java.util.List;
import com.example.incrementalgame.interfaces.Loot;
import com.example.incrementalgame.loot.GoldLoot;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;
import com.example.incrementalgame.loot.Exp;

public class Enemy extends Entity {
    private ResourceManager resourceManager;
    private WaveManager waveManager;
    private float attackTimer = 0f;
    private boolean isAttackReady = true;

    public Enemy(float x, float y, float width, float height, int health, int damage) {
        super(x, y, width, height, health, damage);
    }

    public void update(float deltaTime) {
        if (!isDefeated) {
            attackTimer += deltaTime;
            if (attackTimer >= 2f) {
                isAttackReady = true;
                attackTimer = 0f;
            }
        }
    }

    public void triggerAttack() {
        if (isAttackReady && !isDefeated) {
            isAttackReady = false;
            // Attack logic here
            System.out.println("Enemy attacks!");
        }
    }

    public boolean isAttackReady() {
        return isAttackReady;
    }

    //method to define dropped loot
    public List<Loot> dropLoot() {
        List<Loot> drops = new ArrayList<>();

        float expMultiplier = waveManager.getExpMulti();
        drops.add(new GoldLoot(5000)); //currently hardcoded
        drops.add(new Exp((int) (20 * expMultiplier))); 
        return drops;
    }
    
    protected void onDefeated() {
        super.onDefeated(); //doesnt do anything yet
        //death animation, loot drop, etc
        //applying loot to resource manager, if check was used since I had nullpointer exceptions
        if (resourceManager != null) {
            List<Loot> loots = dropLoot();
            for (Loot loot : loots) {
                loot.apply(resourceManager);
            }
            System.out.println("Enemy defeated!");
        } else {
            System.out.println("Resource Manager is null");
        }

    }
    
     //setting reference to the resource manager
     public void setResourceManager(ResourceManager resourceManager) {
         this.resourceManager = resourceManager;
     }
    
    //setting reference to the wave manager
    public void setWaveManager(WaveManager waveManager) {
        this.waveManager = waveManager;
    }
}
