package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderer {
    private final SpriteBatch batch;

    public Renderer(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Draw a TextureRegion at given coordinates with width and height.
     */
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        batch.draw(region, x, y, width, height);
    }

    /**
     * Call this before any rendering starts.
     */
    public void begin() {
        batch.begin();
    }

    /**
     * Call this after rendering is done.
     */
    public void end() {
        batch.end();
    }
}
