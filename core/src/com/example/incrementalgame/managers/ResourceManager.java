package com.example.incrementalgame.managers;

public class ResourceManager {
    private int gold;
    private int experience;
    private float expMulti;

    public ResourceManager(int initialGold) {
        this.gold = initialGold;
        this.experience = 1000;
        this.expMulti = 1.0f;
    }

    public void addGold(float amount) {
        this.gold += amount;
        // System.out.println("Gold updated: " + gold);
    }

    public void setGold(int amount) {
        this.gold = amount;
    }

    public void setExp(int amount) {
        this.experience = amount;
    }

    public void subtractGold(int amount) {
        this.gold -= amount;
        if (this.gold < 0) this.gold = 0;
    }

    public int getGold() {
        return gold;
    }

    public void addExp(int amount) {
        this.experience += amount * expMulti;
    }

    public int getExp() {
        return experience;
    }

    //set the exp multi and check if it is higher than the current multiplier
    public void setExpMultiplier(float multiplier) {
        if (multiplier > this.expMulti) {
            this.expMulti = multiplier;
        }
    }

    public float getExpMulti() {
        return expMulti;
    }
}
