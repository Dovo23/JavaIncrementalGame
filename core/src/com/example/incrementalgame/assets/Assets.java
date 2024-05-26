package com.example.incrementalgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public Texture groundTexture, playerRunTexture, buttonTexture, enemyTexture, playerTexture;
    public BitmapFont font;
    public Animation<TextureRegion> playerRunAnimation;

    public Assets() {
        try {
            //using LibGdX to load assets, assets are stored in the assets folder
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("redSquare.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            playerTexture = new Texture(Gdx.files.internal("whiteSquare.png"));
            font = new BitmapFont();
            //animations arent ready yet but will be added later

        //try catch for error handeling
        } catch (Exception e) {
            Gdx.app.log("Asset Loading", "Failed to load assets", e);
        }
    }

    public void dispose() {
        groundTexture.dispose();
        playerTexture.dispose();
        enemyTexture.dispose();
        buttonTexture.dispose();
        font.dispose();
    }
}
