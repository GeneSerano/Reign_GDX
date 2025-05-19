package com.southcentralpositronics.reign_gdx.graphics;

import com.southcentralpositronics.reign_gdx.util.FileUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import java.util.HashMap;
import java.util.Map;

public class TileLoader {

    // Color constants for the map (ARGB8888 format)
    public static final int COLOR_GRASS   = 0xFF125B0A;
    public static final int COLOR_HEDGE   = 0xFF004B00;
    public static final int COLOR_WATER   = 0xFFC4DBFF;
    public static final int COLOR_WALL    = 0xFF665C09;
    public static final int COLOR_WALLALT = 0xFF6C3279;
    public static final int COLOR_FLOOR   = 0xFF434343;
    public static final int COLOR_VOID    = 0xFF280A2B;

    /**
     * Loads a mapping from color constants to tile Pixmaps.
     * @param folderPath Folder containing the tile PNGs
     * @return Map from color code to tile Pixmap
     */
    public static Map<Integer, Pixmap> loadTileSet(String folderPath) {
        Map<Integer, Pixmap> tileSet = new HashMap<>();

        tileSet.put(COLOR_GRASS,   loadPixmap(folderPath + "/spawn_grass.png"));
        tileSet.put(COLOR_HEDGE,   loadPixmap(folderPath + "/spawn_hedge.png"));
        tileSet.put(COLOR_WATER,   loadPixmap(folderPath + "/spawn_water.png"));
        tileSet.put(COLOR_WALL,    loadPixmap(folderPath + "/spawn_wall.png"));
        tileSet.put(COLOR_WALLALT, loadPixmap(folderPath + "/spawn_wall_alt.png"));
        tileSet.put(COLOR_FLOOR,   loadPixmap(folderPath + "/spawn_floor.png"));

        // Void tile fallback
        tileSet.put(COLOR_VOID, loadPixmap(folderPath + "/void_tile.png"));

        return tileSet;
    }

    private static Pixmap loadPixmap(String path) {
        return new Pixmap(Gdx.files.internal(path));
    }
}
