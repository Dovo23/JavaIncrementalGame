package com.example.incrementalgame.managers;

public class ResourceManager {
    private int gold;
    private int experience;

    public ResourceManager(int initialGold) {
        this.gold = initialGold;
        this.experience = 1000;
    }

    public void addGold(float amount) {
        this.gold += amount;
        // System.out.println("Gold updated: " + gold);
    }

    public void setGold(int amount) {
        this.gold = amount;
    }

    public void subtractGold(int amount) {
        this.gold -= amount;
        if (this.gold < 0) this.gold = 0;
    }

    public int getGold() {
        return gold;
    }

    public void addExp(int amount) {
        this.experience += amount;
    }

    public int getExp() {
        return experience;
    }
}
