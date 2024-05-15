package com.example.incrementalgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public Texture groundTexture, playerTexture, buttonTexture, enemyTexture;
    public BitmapFont font;

    public Assets() {
        try {
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            playerTexture = new Texture(Gdx.files.internal("whiteSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("redSquare.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            font = new BitmapFont(); 
        } catch (Exception e) {
            Gdx.app.log("Asset Loading", "Failed to load assets", e);
        }
    }

    public void dispose() {
        groundTexture.dispose();
        playerTexture.dispose();
        buttonTexture.dispose();
        font.dispose();
    }
}
