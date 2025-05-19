package com.southcentralpositronics.reign_gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.southcentralpositronics.reign_gdx.graphics.TileLoader;
import com.southcentralpositronics.reign_gdx.graphics.TileMapBuilder;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.Map;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch        batch;
    private Texture            tileMapTexture;
    private Viewport           viewport;
    private OrthographicCamera camera;
    private Vector2i           screemDem;
    private float              scaleFactor;


    @Override
    public void create() {
        batch       = new SpriteBatch();
        screemDem   = new Vector2i(1920, 1080);
        scaleFactor = 4f;
//        image = new Texture("textures/levels/SpawnLevel.png");
//        Map<Integer, Pixmap> tileSet = TileLoader.loadTileSet("core/src/main/resources/spawn_level_tiles/");

        FileHandle PixelMap       = Gdx.files.internal("textures/levels/SpawnLevel_v20.png");
        String     tileFolderPath = "core/src/main/resources/spawn_level_tiles/";
        Gdx.graphics.setWindowedMode(screemDem.x, screemDem.y);

        long startTime = System.nanoTime();

        Pixmap               colorMap      = new Pixmap(PixelMap);
        Map<Integer, Pixmap> tileSet       = TileLoader.loadTileSet(tileFolderPath);
        Pixmap               tileMapPixmap = TileMapBuilder.buildTileMapPixmap(colorMap, tileSet, 16, TileLoader.COLOR_VOID);
        tileMapTexture = new Texture(tileMapPixmap);
        tileMapPixmap.dispose();

        long endTime      = System.nanoTime();
        long durationInMs = (endTime - startTime) / 1_000_000;

        System.out.println("Tile map Pixmap generated in " + durationInMs + " ms");

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(screemDem.x / scaleFactor, screemDem.y / scaleFactor, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

//        Game game = new Game();
//        game.frame.setResizable(true);
//        game.frame.setTitle(Game.title + " | [  " + "0" + " TPS / " + "0" + " FPS  ] | [  X:" + "0" + " Y:" + "0" + "  ]");
//        game.frame.add(game);
//        game.frame.pack();
//        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        game.frame.setLocationRelativeTo(null);
//        game.frame.setVisible(true);
//        game.start();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(tileMapTexture, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tileMapTexture.dispose();
    }
}
