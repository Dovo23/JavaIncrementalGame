package com.example.incrementalgame.loot;

import com.example.incrementalgame.interfaces.Loot;
import com.example.incrementalgame.managers.ResourceManager;

public class GoldLoot implements Loot {
    ResourceManager resourceManager;
    private int amount;


    public GoldLoot(int amount) {
        this.amount = amount;
    }
    
    public void apply(ResourceManager resourceManager) {
        resourceManager.addGold(amount);
    }

}
