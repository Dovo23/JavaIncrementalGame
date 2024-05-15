package com.example.incrementalgame.entities;

public class Player extends Entity {

    public boolean isAlive = true;
    private float initialX;
    private float initialY;
    private int initialHealth;
    public Player(float x, float y, float width, float height, int health, int damage) {
        super(x, y, width, height, health, damage);
        this.initialX = x;
        this.initialY = y;
        this.initialHealth = health;

    }

    public void moveLeft(float speed) {
        bounds.x -= speed;
    }

    public void moveRight(float speed) {
        bounds.x += speed;
    }

    protected void onDefeated() {
        super.onDefeated();
        isAlive = false;
        //death animation, loot drop, etc
        System.out.println("Player defeated!");
    }

    public boolean isAlive() {
        return isAlive;
    }
    
    public void resetPlayer() {
        if (isAlive == false) {
            health = initialHealth; // Reset health
            bounds.x = initialX; // Reset x position
            bounds.y = initialY; // Reset y position
            isAlive = true; // Revive player
        }
    }
}
