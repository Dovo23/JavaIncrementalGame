package com.example.incrementalgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.example.incrementalgame.Player.PlayerLevel;
import com.example.incrementalgame.assets.Assets;
import com.example.incrementalgame.config.GameConfig;
import com.example.incrementalgame.entities.Player;
import com.example.incrementalgame.managers.BuildingManager;
import com.example.incrementalgame.managers.EntityManager;
import com.example.incrementalgame.managers.PrestigeManager;
import com.example.incrementalgame.managers.ResourceManager;
import com.example.incrementalgame.managers.WaveManager;
import com.example.view.BuildingView;
import com.example.view.EntityView;
import com.example.view.HudView;
import com.example.view.PrestigeView;

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
    private EntityView entityView;
    private BuildingView buildingView;
    private PrestigeView prestigeView;
    private HudView hudView;
    private Player player;
    
    public static  int gold = 4000;
    private float ticker = 0;


    @Override
    public void create() {
        //assets and rendering
       assets = new Assets();
       batch = new SpriteBatch();
       camera = new OrthographicCamera();
       viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

       //initialize managers
       resourceManager = new ResourceManager(4000);
       waveManager = new WaveManager();
       entityManager = new EntityManager(assets, resourceManager, waveManager);
       buildingManager = new BuildingManager(assets, resourceManager, entityManager);
       prestigeManager = new PrestigeManager(assets, resourceManager, buildingManager, entityManager, waveManager);

       entityManager.initialize(prestigeManager);
       waveManager.initialize(entityManager);
       //prestigeManager.initialize(entityManager);

       prestigeManager.create();
       waveManager.startNextWave();

       //initialize views
       entityView = new EntityView(assets);
       buildingView = new BuildingView(assets);
       prestigeView = new PrestigeView(assets);
       hudView = new HudView(assets, resourceManager, buildingManager, prestigeManager, waveManager);
    }


    @SuppressWarnings("static-access")
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        handleInput();

        //applying viewport and updating camera
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(assets.backgroundTexture, 0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT); //draws the background to fill the entire game screen

        //updating managers
        entityManager.update(Gdx.graphics.getDeltaTime());
        buildingManager.update(Gdx.graphics.getDeltaTime());

        //rendering views
        entityView.render(batch, entityManager, resourceManager);
        buildingView.render(batch, buildingManager);
        prestigeView.render(batch, prestigeManager);
        hudView.render(batch);

        batch.end();

        updateDynamicLabels(Gdx.graphics.getDeltaTime());
    }
   
    //checks for testing purposes
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
    
    //handles input for the buttons 
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
 
    //updates the dynamic labels like gold
    private void updateDynamicLabels(float delta) {
        ticker += delta;
       
            if (ticker >= 1) {
                resourceManager.addGold((float) (buildingManager.getTotalGoldPerSecond()));
                ticker -= 1; // Reset ticker
            }
        
       
        }
        
    //handling resizing of the window
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(GameConfig.WORLD_WIDTH / 2, GameConfig.WORLD_HEIGHT / 2, 0);
        camera.update();
    }
   
    //removes assets and batch to free up resources
    @Override
    public void dispose() {
        assets.dispose();
        batch.dispose();
    }
}
