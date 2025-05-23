package com.southcentralpositronics.reign_gdx.level.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    public int x, y;
    public boolean       isSolid   = false;
    public tileType      tile_Type = tileType.void_tile;
    public TextureRegion texture;  // NEW

    public Tile(tileType tileType, boolean isSolid) {
        this.tile_Type = tileType;
        this.isSolid   = isSolid;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public enum tileType {
        floor, grass, hedge, wall, wall_alt, water, void_tile
    }

}
