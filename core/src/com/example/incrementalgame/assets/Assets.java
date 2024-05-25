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
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("redSquare.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            playerTexture = new Texture(Gdx.files.internal("whiteSquare.png"));
            font = new BitmapFont();

            // Load and initialize the run animation
            // playerRunTexture = new Texture(Gdx.files.internal("PlayerRun.png"));
            // TextureRegion[][] tmp = TextureRegion.split(playerRunTexture, 27, 45);
            // if (tmp != null && tmp.length > 0 && tmp[0].length == 8) {
            //     TextureRegion[] runFrames = new TextureRegion[8];
            //     int index = 0;
            //     for (int i = 0; i < 8; i++) {
            //         runFrames[index++] = tmp[0][i];
            //     }
            //     playerRunAnimation = new Animation<>(0.1f, runFrames);
            //     Gdx.app.log("Asset Loading", "Animation frames successfully loaded.");
            // } else {
            //     Gdx.app.log("Asset Loading", "Failed to split the sprite sheet correctly or incorrect number of frames");
            // }
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
