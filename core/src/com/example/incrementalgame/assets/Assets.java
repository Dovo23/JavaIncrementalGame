package com.example.incrementalgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public Texture groundTexture, playerRunTexture, buttonTexture, enemyTexture, playerTexture, walkSheet, backgroundTexture;
    public BitmapFont font;
    public Animation<TextureRegion> walkAnimation;

    private int frameCols = 8;
    private int frameRows = 1;

    public Assets() {
        try {
            //using LibGdX to load assets, assets are stored in the assets folder
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("wormSprite.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            playerTexture = new Texture(Gdx.files.internal("whiteSquare.png"));
            backgroundTexture = new Texture(Gdx.files.internal("forest_bg.jpg"));
            font = new BitmapFont();
            //animations arent ready yet but will be added later

            // walkSheet = new Texture(Gdx.files.internal("animation_sheet.png"));  
           walkSheet = new Texture(Gdx.files.internal("playerRun.png"));

            TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / frameCols, walkSheet.getHeight() / frameRows);

             // Place the regions into a 1D array in the correct order
            TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
            int index = 0;
            for (int i = 0; i < frameRows; i++) {
                for (int j = 0; j < frameCols; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }

            // Initialize the Animation with the frame interval and array of frames
            walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

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
        backgroundTexture.dispose();
        font.dispose();
    }
}
