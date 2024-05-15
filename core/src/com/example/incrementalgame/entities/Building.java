package com.example.incrementalgame.entities;

import com.example.incrementalgame.IncrementalGame;

public class Building {
    private int level;
    private int baseCost;
    private float baseIncomePerSecond;
    private float incomePerSecond;
    private float totalMulti = 1.0f; 
    @SuppressWarnings("unused")
    private String name;

    public Building(String name, int baseCost, float baseIncomePerSecond) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseIncomePerSecond = baseIncomePerSecond;
        this.incomePerSecond = baseIncomePerSecond;
        this.level = 0;
    }

    public void buy() {
        if (IncrementalGame.gold >= getCost()) {
            IncrementalGame.gold -= getCost();
            level++;
        }
    }

    public void resetWithMultiplier() {
        level = 0;
        totalMulti *= 1.25; // Increase multiplier by 25% each prestige
    }

    public int getCost() {
        return (int) (baseCost * Math.pow(1.15, level));
    }

    public float getIncomePerSecond() {
        return incomePerSecond * level * totalMulti;
    }

    public float getIncomePerSecondBase() {
        return baseIncomePerSecond * totalMulti;
    }
}
