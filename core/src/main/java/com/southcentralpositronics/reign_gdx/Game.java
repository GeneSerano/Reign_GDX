package com.southcentralpositronics.reign_gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.southcentralpositronics.reign_gdx.entity.mob.PlayerMob;
import com.southcentralpositronics.reign_gdx.input.Keyboard;
import com.southcentralpositronics.reign_gdx.input.Mouse;
import com.southcentralpositronics.reign_gdx.level.Level;
import com.southcentralpositronics.reign_gdx.level.tile.TileCoordinates;

public class Game extends ApplicationAdapter {
    public static final int WIDTH = 480 - 85;
    public static final int HEIGHT = 270;
    public static final int SCALE = 3;
    public static final String TITLE = "Reign";

    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Keyboard keyboard;
    private Mouse mouse;
    private Level level;
    private PlayerMob player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        keyboard = new Keyboard();
        mouse = new Mouse();
        Gdx.input.setInputProcessor(new com.badlogic.gdx.InputMultiplexer(keyboard, mouse));

        atlas = new TextureAtlas(Gdx.files.internal("sprites/player.atlas"));

        level = Level.spawn;
        TileCoordinates playerSpawn = new TileCoordinates(20, 42);
        player = new PlayerMob(playerSpawn.getX(), playerSpawn.getY(), "King Steve", keyboard, atlas);
        level.add(player);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Update game logic
        keyboard.update();
        player.update(delta);
        level.update(delta);

        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Render game
        batch.begin();
        level.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        // Dispose of any other resources you manually load
    }

    public static int getWindowWidth() {
        return WIDTH * SCALE;
    }

    public static int getWindowHeight() {
        return HEIGHT * SCALE;
    }
}
