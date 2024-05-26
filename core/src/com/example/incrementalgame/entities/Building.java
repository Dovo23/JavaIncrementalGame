package com.example.incrementalgame.entities;


public class Building {
    private int level;
    private int baseCost;
    private float baseIncomePerSecond;
    private float incomePerSecond;
    private float totalMulti = 1.0f; 
    private String name;


    public Building(String name, int baseCost, float baseIncomePerSecond) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseIncomePerSecond = baseIncomePerSecond;
        this.incomePerSecond = baseIncomePerSecond;
        this.level = 0;
    }

    // Reset the building level and increase the total multiplier
    public void resetWithMultiplier() {
        level = 0;
        totalMulti *= 1.25; //increase multiplier by 25% each prestige
    }

    // Get the cost of the upgrade based on the level
    public int getCost() {
        return (int) (baseCost * Math.pow(1.15, level));
    }

    public float getIncomePerSecond() {
        return incomePerSecond * level * totalMulti;
    }

    public float getIncomePerSecondBase() {
        return baseIncomePerSecond * totalMulti;
    }

    //get the building name, required for the building manager
    public Object getName() {
        return name;
    }
    
    public void increaseLevel() {
        level++;
    }

}
