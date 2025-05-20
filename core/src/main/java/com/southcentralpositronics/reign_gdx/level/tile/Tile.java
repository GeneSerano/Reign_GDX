package com.southcentralpositronics.reign_gdx.level.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    public int x, y;
    public Sprite  sprite;
    public boolean isSolid = false;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Render this tile at the given tile coordinates,
     * applying the scroll offsets for camera movement.
     *
     * @param batch   The SpriteBatch to draw with
     * @param xScroll The x camera offset in pixels
     * @param yScroll The y camera offset in pixels
     */
    public void render(SpriteBatch batch, int xScroll, int yScroll) {
        if (sprite == null) return;

        // Calculate screen position by shifting tile coords by tile size (16) and subtracting scroll
        float screenX = (x << 4) - xScroll;
        float screenY = (y << 4) - yScroll;

        sprite.setPosition(screenX, screenY);
        sprite.draw(batch);
    }

    /**
     * Whether this tile blocks movement.
     * Override in subclasses as needed.
     */
    public boolean isSolid() {
        return isSolid;
    }
}
