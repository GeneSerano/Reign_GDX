package com.southcentralpositronics.reign_gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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
    public static final int           WIDTH           = (int) (1920);
    public static final int           HEIGHT          = (int) (1080);
    public static final int           SCALE           = 4;
    public static final int           VIEWPORT_WIDTH  = WIDTH / SCALE;
    public static final int           VIEWPORT_HEIGHT = HEIGHT / SCALE;
    public static final String        TITLE           = "Reign";
    public static       int[]         screenCenter    = new int[2];
    public static       float         updateInt;
    private             EventListener eventListener;
    protected           ShapeRenderer shapeRenderer;
    protected           String        mapPath;


    private SpriteBatch  batch;
    private Viewport     viewport;
    private TextureAtlas mobAtlas, tileAtlas;
    private Keyboard           keyboard;
    private Mouse              mouse;
    private Level              level;
    private PlayerMob          player;
    private OrthographicCamera camera = new OrthographicCamera();

    @Override
    public void create() {
        String mobAtlasPath  = "atlas/Mobs.atlas";
        String tileAtlasPath = "atlas/Tiles.atlas";
        mapPath       = "levels/SpawnLevel_v20.png";
        mobAtlas      = new TextureAtlas(Gdx.files.internal(mobAtlasPath));
        tileAtlas     = new TextureAtlas(Gdx.files.internal(tileAtlasPath));
        shapeRenderer = new ShapeRenderer();

        screenCenter[0] = VIEWPORT_WIDTH / 2;
        screenCenter[1] = VIEWPORT_HEIGHT / 2;
        batch           = new SpriteBatch();
        mobAtlas        = new TextureAtlas(Gdx.files.internal(mobAtlasPath));
        keyboard        = new Keyboard();
        mouse           = new Mouse(new EventListener() {
            @Override
            public void onEvent(Event event) {

            }
        });
        Gdx.input.setInputProcessor(new InputMultiplexer(keyboard, mouse));


        viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        viewport.apply();
        camera = (OrthographicCamera) viewport.getCamera();
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
        camera.update();


        TileCoordinates playerSpawn = new TileCoordinates(20, 42);
        player = new PlayerMob(this, 200, 200, "King Steve");
        level  = new Level(this);
        level.add(player);
        level.setPlayer(player);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    //    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

//        System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Update game logic
        keyboard.update();
        player.update(delta);
        level.update();

        // You can move camera here if following the player, etc.
        // camera.position.set(...);
        float halfWidth  = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        int mapPixelWidth  = level.getWidth() * 16;
        int mapPixelHeight = level.getHeight() * 16;

        // Clamp X
        float cameraX = MathUtils.clamp((int) player.getX(), halfWidth, mapPixelWidth - halfWidth);
        // Clamp Y
        float cameraY = MathUtils.clamp((int) player.getY(), halfHeight, mapPixelHeight - halfHeight);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);


        // Render game
        batch.begin();
        batch.enableBlending();
        level.render(batch);
        for (PlayerMob p : level.getPlayers()) p.render(batch);
        batch.end();

        float thickness = 1.5f; // Set your desired border thickness

        float hitboxX      = (float) player.getX() + player.getPaddingLeft();
        float hitboxY      = (float) player.getY() + player.getPaddingBottom();
        float hitboxWidth  = player.getWidth() - player.getPaddingLeft() - player.getPaddingRight();
        float hitboxHeight = player.getHeight() - player.getPaddingTop() - player.getPaddingBottom();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        // Left border
        shapeRenderer.rect(hitboxX, hitboxY, thickness, hitboxHeight);
        // Right border
        shapeRenderer.rect(hitboxX + hitboxWidth - thickness, hitboxY, thickness, hitboxHeight);
        // Bottom border
        shapeRenderer.rect(hitboxX, hitboxY, hitboxWidth, thickness);
        // Top border
        shapeRenderer.rect(hitboxX, hitboxY + hitboxHeight - thickness, hitboxWidth, thickness);
        shapeRenderer.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        mobAtlas.dispose();
        // Dispose of any other resources you manually load
    }

    public static int getWindowWidth() {
        return VIEWPORT_WIDTH * SCALE;
    }

    public static int getWindowHeight() {
        return VIEWPORT_HEIGHT * SCALE;
    }

    public Level getLevel() {
        return level;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public TextureAtlas getMobAtlas() {
        return mobAtlas;
    }

    public TextureAtlas getTileAtlas() {
        return tileAtlas;
    }

    public String getMapPath() {
        return mapPath;
    }
}
