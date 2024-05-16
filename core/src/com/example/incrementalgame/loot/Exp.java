package com.example.incrementalgame.loot;

import com.example.incrementalgame.interfaces.Loot;
import com.example.incrementalgame.managers.ResourceManager;

public class Exp implements Loot {
    private int exp;

    public Exp(int exp) {
        this.exp = exp;
    }

    @Override
    public void apply(ResourceManager resourceManager) {
        resourceManager.addExp(exp);
    }
}