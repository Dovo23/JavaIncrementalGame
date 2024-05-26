package com.example.incrementalgame.managers;

import com.example.incrementalgame.entities.Player;

public class AgeManager {
    private float ageTicker = 0;
    private Player player;
    private float second = 1.0f; // One second interval for demonstration purposes but can be adjusted

    public AgeManager(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        ageTicker += delta;
        if (ageTicker >= second) {
            player.addAge();
            ageTicker -= second; // Reset ticker
        }
    }

    public int getPlayerAge() {
        return player.getAge();
    }
}
