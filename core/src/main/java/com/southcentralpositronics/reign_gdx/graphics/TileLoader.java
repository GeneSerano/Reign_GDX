package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class TileLoader {

    // Color constants (ARGB8888)
    public static final int COLOR_GRASS   = 0xFF125B0A;
    public static final int COLOR_HEDGE   = 0xFF004B00;
    public static final int COLOR_WATER   = 0xFFC4DBFF;
    public static final int COLOR_WALL    = 0xFF665C09;
    public static final int COLOR_WALLALT = 0xFF6C3279;
    public static final int COLOR_FLOOR   = 0xFF434343;
    public static final int COLOR_VOID    = 0xFF280A2B;

    public static Map<Integer, Tile> loadTiles(TextureAtlas atlas) {
        Map<Integer, Tile> tileMap = new HashMap<>();

        tileMap.put(COLOR_GRASS,   createTile(Tile.tileType.grass, false, atlas, "grass"));
        tileMap.put(COLOR_HEDGE,   createTile(Tile.tileType.hedge, true, atlas, "hedge"));
        tileMap.put(COLOR_WATER,   createTile(Tile.tileType.water, false, atlas, "water"));
        tileMap.put(COLOR_WALL,    createTile(Tile.tileType.wall, true,  atlas, "wall"));
        tileMap.put(COLOR_WALLALT, createTile(Tile.tileType.wall_alt, true, atlas, "wall_alt"));
        tileMap.put(COLOR_FLOOR,   createTile(Tile.tileType.floor, false, atlas, "floor"));
        tileMap.put(COLOR_VOID,    createTile(Tile.tileType.void_tile, false, atlas, "void_tile")); // fallback

        return tileMap;
    }

    private static Tile createTile(Tile.tileType type, boolean solid, TextureAtlas atlas, String regionName) {
        Tile tile = new Tile(type, solid);
        tile.setTexture(atlas.findRegion(regionName));
        return tile;
    }

}
