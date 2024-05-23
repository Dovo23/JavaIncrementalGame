package com.example.incrementalgame.entities;

import java.util.ArrayList;
import java.util.List;
import com.example.incrementalgame.interfaces.Loot;
import com.example.incrementalgame.loot.GoldLoot;
import com.example.incrementalgame.managers.PrestigeManager;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;
import com.example.incrementalgame.loot.Exp;

public class Enemy extends Entity {
    private ResourceManager resourceManager;
    private WaveManager waveManager;
    public Enemy(float x, float y, float width, float height, int health, int damage) {
        super(x, y, width, height, health, damage);
    }

    public List<Loot> dropLoot() {
        List<Loot> drops = new ArrayList<>();

        float expMultiplier = waveManager.getExpMulti();
        drops.add(new GoldLoot(5000));
        drops.add(new Exp((int) (20 * expMultiplier)));
        return drops;
    }
    
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
    
    protected void onDefeated() {
        super.onDefeated();
        //death animation, loot drop, etc
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
    
    public void setWaveManager(WaveManager waveManager) {
        this.waveManager = waveManager;
    }
}
