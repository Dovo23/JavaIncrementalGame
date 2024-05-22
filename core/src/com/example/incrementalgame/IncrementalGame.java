package com.example.incrementalgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.GameButton;
import com.example.incrementalgame.entities.Player;
import com.example.incrementalgame.managers.BuildingManager;
import com.example.incrementalgame.managers.EntityManager;
import com.example.incrementalgame.managers.PrestigeManager;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;

public class IncrementalGame extends ApplicationAdapter {
    private Assets assets;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    // private GameButton prestigeButton;
    private ResourceManager resourceManager;
    private EntityManager entityManager;
    private BuildingManager buildingManager;
    private PrestigeManager prestigeManager;
    private WaveManager waveManager;
    private Player player;
    
    public static  int gold = 4000;
    private float ticker = 0;


    @Override
    public void create() {
       assets = new Assets();
       batch = new SpriteBatch();
       camera = new OrthographicCamera();
       viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

       resourceManager = new ResourceManager(4000);

       waveManager = new WaveManager();
       entityManager = new EntityManager(assets, resourceManager, waveManager);
       waveManager.initialize(entityManager);

       buildingManager = new BuildingManager(assets, resourceManager);
       prestigeManager = new PrestigeManager(assets, resourceManager, buildingManager);

       prestigeManager.create();
       waveManager.startNextWave();
    }


    @SuppressWarnings("static-access")
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        // checkResourceManager();
        Checks();
        handleInput();

        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(assets.groundTexture, 0, 0, GameConfig.WORLD_WIDTH, 50);

        entityManager.render(batch);
        entityManager.update(Gdx.graphics.getDeltaTime());

        buildingManager.render(batch);
        buildingManager.update(Gdx.graphics.getDeltaTime());

        prestigeManager.render(batch);

        assets.font.draw(batch, "Gold: " + resourceManager.getGold(), 10, GameConfig.WORLD_HEIGHT - 20);
        assets.font.draw(batch, buildingManager.getTotalGoldPerSecond() + " gold/s", 10, GameConfig.WORLD_HEIGHT - 40);
        assets.font.draw(batch, "Prestige Level: " + prestigeManager.getPrestigeLevel(), 10, GameConfig.WORLD_HEIGHT - 60);
        assets.font.draw(batch, "Required gold till next prestige: " + (int) prestigeManager.getNextPrestigeRequirement(), 10, GameConfig.WORLD_HEIGHT - 80);
        assets.font.draw(batch, "Wave: " + (int) waveManager.getWaveNumber(), 10, GameConfig.WORLD_HEIGHT - 100);
        
       //maybe will be moved somewhere else later
        

        batch.end();

        updateDynamicLabels(Gdx.graphics.getDeltaTime());
    }
   
    private void checkResourceManager() {
        if (resourceManager == null) {
            System.out.println("Entity Manager is null");
        } else {
            System.out.println("Entity Manager is not null");
        }
    }
    
    private void Checks() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            System.out.println("HP: " + player.getHealth() + " Damage: " + player.getDamage() + " Level: "
                    + player.getLevel() + " Title: " + player.getTitle());
        }

    }
    
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            float x = touchPos.x;
            float y = touchPos.y;
            buildingManager.handleButtonInput(x, y);
            prestigeManager.handleButtonInput(x, y);
        }
    }

    private void updateDynamicLabels(float delta) {
        ticker += delta;
       
            if (ticker >= 1) {
                resourceManager.addGold((float) (buildingManager.getTotalGoldPerSecond()));
                ticker -= 1; // Reset accumulator
            }
        
       
        }
        

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(GameConfig.WORLD_WIDTH / 2, GameConfig.WORLD_HEIGHT / 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        assets.dispose();
        batch.dispose();
    }
}
