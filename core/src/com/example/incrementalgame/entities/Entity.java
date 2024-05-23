package com.example.incrementalgame.entities;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    protected Rectangle bounds;
    protected int health;  //acts as max health
    protected int damage;
    protected int currentHealth;
    protected int currentDamage;
    protected boolean isDefeated = false;

    public Entity(float x, float y, float width, float height, int health, int damage) {
        this.bounds = new Rectangle(x, y, width, height);
        this.health = health;
        this.damage = damage;
        this.currentHealth = health;
        this.currentDamage = damage;
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
            setDefeated(true);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDefeated(boolean defeated) {
        this.isDefeated = defeated;
        if (defeated) {
            onDefeated();
        }
    }

    public boolean isDefeated() {
    return isDefeated;
    }


    protected void onDefeated() {
        //default death behavior
    }
}
