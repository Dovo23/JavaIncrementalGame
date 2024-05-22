package com.example.incrementalgame.Player;

import com.example.incrementalgame.entities.Player;
import com.example.incrementalgame.managers.ResourceManager;

public class PlayerLevel {
    private int level;
    private int nextLevelExp;
    private String title;
    private ResourceManager resourceManager;

    public PlayerLevel(ResourceManager resourceManager) {
       this.resourceManager = resourceManager;
        this.level = 1;
        this.nextLevelExp = 20;  
        this.title = "Beggar";  
    }

    public void checkExpThreshold(Player player) {
        System.out.println("Current EXP: " + resourceManager.getExp() + ", Required for next level: " + nextLevelExp);
       while (resourceManager.getExp() >= nextLevelExp) {
           levelUp(player);
           System.out.println("Level up! Current level: " + level);
           }
    }

   private void levelUp(Player player) {
        level++;
        resourceManager.addExp(-nextLevelExp);
        nextLevelExp = calculateNextLevelExp();
        updateTitle(player);
        player.updateStats(1.1f);
        System.out.println("Leveled Up! New Level: " + level + ", Next Level EXP: " + nextLevelExp);
}
    

    public int calculateNextLevelExp() {
        return (int) (nextLevelExp * 1.2); 
    }

    private void updateTitle(Player player) {
        switch (level) {
            case 5:
                title = "Peasant";
                player.increaseBaseStats(5, 50);
                break;
            case 15:
                title = "Farmer";
                player.increaseBaseStats(15, 100);
                break;
            case 35:
                title = "Merchant";
                player.increaseBaseStats(35, 175);
                break;
            case 50:
                title = "Knight";
                player.increaseBaseStats(60, 250);
                break;
            case 65:
                title = "Baron";
                player.increaseBaseStats(80, 400);
                break;
            case 80:
                title = "Duke";
                player.increaseBaseStats(100, 750);
                break;
            case 100:
                title = "King";
                player.increaseBaseStats(200, 1500);
                break;    
        }
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public void outPutNextExp() {
        System.out.println("Next level exp: " + nextLevelExp);
    }
}
