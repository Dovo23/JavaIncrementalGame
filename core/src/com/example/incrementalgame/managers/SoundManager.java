// package com.example.incrementalgame.managers;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.audio.Sound;
// import com.badlogic.gdx.audio.Music;

// public class SoundManager {
//     private Sound attackSound;
//     private Sound playerHitSound;
//     private Music backgroundMusic;

//     public SoundManager() {
//         try {
//             attackSound = Gdx.audio.newSound(Gdx.files.internal("attack.wav"));
//             playerHitSound = Gdx.audio.newSound(Gdx.files.internal("playerHit.mp3"));
//             backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//         } catch (Exception e) {
//             Gdx.app.log("Sound Loading", "Failed to load sounds", e);
//         }
//     }

//     public void playAttackSound() {
//         attackSound.play();
//     }

//     public void playPlayerHitSound() {
//         playerHitSound.play();
//     }

//     public void playBackgroundMusic() {
//         backgroundMusic.setLooping(true);
//         backgroundMusic.play();
//     }

//     public void stopBackgroundMusic() {
//         backgroundMusic.stop();
//     }

//     public void dispose() {
//         attackSound.dispose();
//         playerHitSound.dispose();
//         backgroundMusic.dispose();
//     }
// }
