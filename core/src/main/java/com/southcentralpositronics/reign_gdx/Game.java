package com.southcentralpositronics.reign_gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.events.Event;
import com.southcentralpositronics.reign_gdx.events.EventListener;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;
import com.southcentralpositronics.reign_gdx.level.Level;
import com.southcentralpositronics.reign_gdx.level.tile.TileCoordinates;

public class Game extends ApplicationAdapter {
    public static final int           WIDTH        = 1920;
    public static final int           HEIGHT       = 1080;
    public static final int           SCALE        = 4;
    public static final String        TITLE        = "Reign";
    public static       int[]         screenCenter = new int[2];
    public static       float         updateInt;
    private             EventListener eventListener;

    private SpriteBatch  batch;
    private Viewport     viewport;
    private TextureAtlas mobAtlas;
    private Keyboard     keyboard;
    private Mouse        mouse;
    private Level        level;
    private PlayerMob    player;

    @Override
    public void create() {
        String mobAtlasPath  = "core/src/main/resources/atlas/Mobs.atlas";
        String mapPath       = "core/src/main/resources/levels/SpawnLevel_v20.png";
        String tileAtlasPath = "core/src/main/resources/atlas/Tiles.atlas";

        Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);

        screenCenter[0] = WIDTH / 2;
        screenCenter[1] = HEIGHT / 2;
        batch           = new SpriteBatch();
        mobAtlas        = new TextureAtlas(Gdx.files.internal(mobAtlasPath));
        keyboard        = new Keyboard();
        mouse           = new Mouse(new EventListener() {
            @Override
            public void onEvent(Event event) {

            }
        });
        Gdx.input.setInputProcessor(new com.badlogic.gdx.InputMultiplexer(keyboard, mouse));

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH / SCALE, HEIGHT / SCALE, camera);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

        camera.position.set(screenCenter[0], screenCenter[1], 0);
        camera.update();
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);


        TileCoordinates playerSpawn = new TileCoordinates(20, 42);
        player = new PlayerMob(0, 0, "King Steve", keyboard, mobAtlasPath);
        level  = new Level(tileAtlasPath, mapPath);
        level.add(player);
        level.setPlayer(player);
    }

    public void resize() {
        Camera camera = viewport.getCamera();
        screenCenter[0] = WIDTH / 2;
        screenCenter[1] = HEIGHT / 2;
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void render() {
        updateInt += 0.1f;
        if (updateInt > Float.MAX_VALUE - 1) updateInt = 0;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        float delta = Gdx.graphics.getDeltaTime();
//
//        // Update game logic
//        keyboard.update();
//        player.update();
//        level.update();

        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        // Render game
        batch.begin();
        level.render(batch);
        player.render(batch, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mobAtlas.dispose();
        // Dispose of any other resources you manually load
    }

    public static int getWindowWidth() {
        return WIDTH * SCALE;
    }

    public static int getWindowHeight() {
        return HEIGHT * SCALE;
    }

    public Level getLevel() {
        return level;
    }
}
