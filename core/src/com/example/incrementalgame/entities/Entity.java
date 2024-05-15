package com.example.incrementalgame.entities;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    protected Rectangle bounds;
    protected int health;
    protected int damage;
    protected boolean isDefeated = false;

    public Entity(float x, float y, float width, float height, int health, int damage) {
        this.bounds = new Rectangle(x, y, width, height);
        this.health = health;
        this.damage = damage;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
            setDefeated(true);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
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
