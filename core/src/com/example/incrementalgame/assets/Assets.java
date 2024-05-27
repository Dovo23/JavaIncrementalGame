package com.example.incrementalgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public Texture groundTexture, playerRunTexture, buttonTexture, enemyTexture, playerTexture, walkSheet, attackSheet, defeatSheet, backgroundTexture;
    public BitmapFont font;
    public Animation<TextureRegion> walkAnimation, attackAnimation, defeatAnimation;


    public Assets() {
        try {
            //using LibGdX to load assets, assets are stored in the assets folder
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("wormSprite.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            playerTexture = new Texture(Gdx.files.internal("playerSprite.png"));
            backgroundTexture = new Texture(Gdx.files.internal("forest_bg.jpg"));
            font = new BitmapFont();
            //animations arent ready yet but will be added later

            // walkSheet = new Texture(Gdx.files.internal("animation_sheet.png"));  
            walkSheet = new Texture(Gdx.files.internal("playerRun.png"));

            TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 8, walkSheet.getHeight());

            // Place the regions into a 1D array in the correct order
            TextureRegion[] walkFrames = new TextureRegion[8];
            for (int i = 0; i < 8; i++) {
                walkFrames[i] = tmp[0][i];
            }
            // Initialize the Animation with the frame interval and array of frames
            walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

            attackSheet = new Texture(Gdx.files.internal("playerAttack.png"));
            TextureRegion[][] attackTmp = TextureRegion.split(attackSheet, attackSheet.getWidth() / 7, attackSheet.getHeight());
            TextureRegion[] attackFrames = new TextureRegion[7];
            for (int i = 0; i < 7; i++) {
                attackFrames[i] = attackTmp[0][i];
            }
            attackAnimation = new Animation<>(0.1f, attackFrames);

            defeatSheet = new Texture(Gdx.files.internal("playerDefeat.png"));
            TextureRegion[][] defeatTmp = TextureRegion.split(defeatSheet, defeatSheet.getWidth() / 7, defeatSheet.getHeight());
            TextureRegion[] defeatFrames = new TextureRegion[7];
            for (int i = 0; i < 7; i++) {
                defeatFrames[i] = defeatTmp[0][i];
            }
            defeatAnimation = new Animation<>(0.1f, defeatFrames);

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
        walkSheet.dispose();
        attackSheet.dispose();
        defeatSheet.dispose();
        font.dispose();
    }
}
