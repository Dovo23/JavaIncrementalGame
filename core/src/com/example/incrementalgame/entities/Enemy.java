package com.example.incrementalgame.entities;

public class Enemy extends Entity {
    public Enemy(float x, float y, float width, float height, int health, int damage) {
        super(x, y, width, height, health, damage);
    }
    
    protected void onDefeated() {
        super.onDefeated();
        //death animation, loot drop, etc
        System.out.println("Enemy defeated!");
    }
}
