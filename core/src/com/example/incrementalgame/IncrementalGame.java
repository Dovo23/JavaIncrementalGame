package com.example.incrementalgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.GameButton;
import com.example.incrementalgame.managers.BuildingManager;
import com.example.incrementalgame.managers.EntityManager;
import com.example.incrementalgame.managers.ResourceManager;

public class IncrementalGame extends ApplicationAdapter {
    private Assets assets;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private GameButton prestigeButton;
    private ResourceManager resourceManager;
    private EntityManager entityManager;
    private BuildingManager buildingManager;
    
    

    public static  int gold = 4000;
    private float goldAccumulator = 0;
    private int prestigeLevel = 0;
    private double nextPrestigeRequirement = 5000;


    @Override
    public void create() {
       assets = new Assets();
       batch = new SpriteBatch();
       camera = new OrthographicCamera();
       viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

       resourceManager = new ResourceManager(4000);
       entityManager = new EntityManager(assets, resourceManager);
       buildingManager = new BuildingManager(assets, resourceManager);
       

       prestigeButton = new GameButton(650, 150, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, "Prestige");
    }


    @SuppressWarnings("static-access")
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        checkResourceManager();
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

        //prestige button appears if requirement met
        if (resourceManager.getGold() >= nextPrestigeRequirement) {
            prestigeButton.draw(batch, assets.font, assets.buttonTexture);
        }

        
        assets.font.draw(batch, "Gold: " + resourceManager.getGold(), 10, GameConfig.WORLD_HEIGHT - 20);
        assets.font.draw(batch, buildingManager.getTotalGoldPerSecond() + " gold/s", 10, GameConfig.WORLD_HEIGHT - 40);
        assets.font.draw(batch, "Prestige Level: " + prestigeLevel, 10, GameConfig.WORLD_HEIGHT - 60);
        assets.font.draw(batch, "Required gold till next prestige: " + (int) nextPrestigeRequirement, 10, GameConfig.WORLD_HEIGHT - 80);
    
        batch.end();

        updateIncome(Gdx.graphics.getDeltaTime());
    }
   
    private void checkResourceManager() {
        if (resourceManager == null) {
            System.out.println("Entity Manager is null");
        }
        else {
            System.out.println("Entity Manager is not null");
        }
    }
    
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            float x = touchPos.x;
            float y = touchPos.y;
            buildingManager.handleButtonInput(x, y);

            if (prestigeButton.getBounds().contains(x, y) && resourceManager.getGold() >= nextPrestigeRequirement) {
                performPrestige();
            }
        }
    }

   //gotta fix the building references
    private void performPrestige() {
    resourceManager.setGold(100);
    buildingManager.getBuildingByName("Miner").resetWithMultiplier();
    buildingManager.getBuildingByName("Bakery").resetWithMultiplier();
    buildingManager.getBuildingByName("Factory").resetWithMultiplier();
    prestigeLevel++;
    nextPrestigeRequirement *= 2; // Double the requirement for the next prestige
}
    
    private void updateIncome(float delta) {
        goldAccumulator += delta;
        if (resourceManager != null) {
            if (goldAccumulator >= 1) {
                resourceManager.addGold((int) (buildingManager.getTotalGoldPerSecond()));
                goldAccumulator -= 1; // Reset accumulator
            }
        }
       else {
            System.out.println("Resource Manager is null");
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
