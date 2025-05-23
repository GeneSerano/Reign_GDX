package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

import java.util.Map;

public class TileMapBuilder {

    /**
     * Builds a 2D array of Tiles based on the input color map.
     *
     * @param colorMap  A Pixmap where each pixel color represents a tile type
     * @param tileTypes A map from pixel ARGB color (int) to Tile instances (with textures already set)
     * @return A 2D array of Tile objects representing the level
     */
    public static Tile[][] buildTileGrid(Pixmap colorMap, Map<Integer, Tile> tileTypes) {
        int width = colorMap.getWidth();
        int height = colorMap.getHeight();
        Tile[][] tileGrid = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = colorMap.getPixel(x, y);
                int r = (rgba >> 24) & 0xFF;
                int g = (rgba >> 16) & 0xFF;
                int b = (rgba >> 8) & 0xFF;
                int a = rgba & 0xFF;
                int pixelColor = (a << 24) | (r << 16) | (g << 8) | b;

                Tile baseTile = tileTypes.get(pixelColor);
                if (baseTile == null) {
                    System.out.printf("Unknown tile color: 0x%08X at (%d, %d)%n", pixelColor, x, y);
                    baseTile = tileTypes.getOrDefault(0xFF280A2B, new Tile(Tile.tileType.void_tile, false)); // fallback to void
                }

                Tile tileInstance = new Tile(baseTile.tile_Type, baseTile.isSolid());
                tileInstance.x = x;
                tileInstance.y = y;
                tileInstance.setTexture(baseTile.getTexture());

                tileGrid[x][y] = tileInstance;
            }
        }

        return tileGrid;
    }
}
