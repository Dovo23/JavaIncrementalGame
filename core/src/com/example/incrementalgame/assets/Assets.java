package com.example.incrementalgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.example.incrementalgame.managers.SoundManager;

public class Assets {
    public Texture groundTexture, playerRunTexture, buttonTexture, enemyTexture, playerTexture,
            walkSheet, attackSheet, defeatSheet, wormAttackSheet, wormDefeatSheet, backgroundTexture;
    public BitmapFont font;
    public Animation<TextureRegion> walkAnimation, attackAnimation, defeatAnimation, wormAttackAnimation,
            wormDefeatAnimation;
    public Sound playerHitSound;
    private Sound playerAttackSound;

    public Assets() {
        try {
            // using LibGdX to load assets, assets are stored in the assets folder
            groundTexture = new Texture(Gdx.files.internal("greenSquare.png"));
            enemyTexture = new Texture(Gdx.files.internal("wormSprite.png"));
            buttonTexture = new Texture(Gdx.files.internal("button.png"));
            playerTexture = new Texture(Gdx.files.internal("playerSprite.png"));
            backgroundTexture = new Texture(Gdx.files.internal("forest_bg.jpg"));
            font = new BitmapFont();
            //soundManager = new SoundManager();

            // animations
            walkSheet = new Texture(Gdx.files.internal("playerRun.png"));
            TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 8, walkSheet.getHeight());
            TextureRegion[] walkFrames = new TextureRegion[8];
            for (int i = 0; i < 8; i++) {
                walkFrames[i] = tmp[0][i];
            }
            walkAnimation = new Animation<>(0.1f, walkFrames);

            attackSheet = new Texture(Gdx.files.internal("playerAttack.png"));
            TextureRegion[][] attackTmp = TextureRegion.split(attackSheet, attackSheet.getWidth() / 7,
                    attackSheet.getHeight());
            TextureRegion[] attackFrames = new TextureRegion[7];
            for (int i = 0; i < 7; i++) {
                attackFrames[i] = attackTmp[0][i];
            }
            attackAnimation = new Animation<>(0.1f, attackFrames);

            defeatSheet = new Texture(Gdx.files.internal("playerDefeat.png"));
            TextureRegion[][] defeatTmp = TextureRegion.split(defeatSheet, defeatSheet.getWidth() / 7,
                    defeatSheet.getHeight());
            TextureRegion[] defeatFrames = new TextureRegion[7];
            for (int i = 0; i < 7; i++) {
                defeatFrames[i] = defeatTmp[0][i];
            }
            defeatAnimation = new Animation<>(0.1f, defeatFrames);

            // worm animations
            wormAttackSheet = new Texture(Gdx.files.internal("wormAttack.png"));
            TextureRegion[][] wormAttackTmp = TextureRegion.split(wormAttackSheet, wormAttackSheet.getWidth() / 16,
                    wormAttackSheet.getHeight());
            TextureRegion[] wormAttackFrames = new TextureRegion[16];
            for (int i = 0; i < 16; i++) {
                wormAttackFrames[i] = wormAttackTmp[0][i];
            }
            wormAttackAnimation = new Animation<>(0.1f, wormAttackFrames);

            wormDefeatSheet = new Texture(Gdx.files.internal("wormDefeat.png"));
            TextureRegion[][] wormDefeatTmp = TextureRegion.split(wormDefeatSheet, wormDefeatSheet.getWidth() / 8,
                    wormDefeatSheet.getHeight());
            TextureRegion[] wormDefeatFrames = new TextureRegion[8]; 
            for (int i = 0; i < 8; i++) {
                wormDefeatFrames[i] = wormDefeatTmp[0][i];
            }
            wormDefeatAnimation = new Animation<>(0.1f, wormDefeatFrames);

            playerHitSound = Gdx.audio.newSound(Gdx.files.internal("playerHit.mp3"));
            playerAttackSound = Gdx.audio.newSound(Gdx.files.internal("playerAttack.mp3"));

        } catch (Exception e) {
            Gdx.app.log("Asset Loading", "Failed to load assets", e);
        }
    }
    
    public void playPlayerHitSound() {
        playerHitSound.play();
    }

    public void playPlayerAttackSound() {
        playerAttackSound.play();
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
        playerHitSound.dispose();
    }
}
