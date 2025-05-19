package com.southcentralpositronics.reign_gdx.graphics;

import com.badlogic.gdx.graphics.Pixmap;

import java.util.HashMap;
import java.util.Map;

//public class TileMapBuilder {
//
//    public static Pixmap buildTileMapPixmap(Pixmap colorMap, Map<Integer, Pixmap> tileSet, int tileSize, int voidColor) {
//        int width  = colorMap.getWidth();
//        int height = colorMap.getHeight();
//
//        Pixmap result = new Pixmap(width * tileSize, height * tileSize, Pixmap.Format.RGBA8888);
//
//        // Cache to avoid repeated lookups from tileSet
//        Map<Integer, Pixmap> tileCache = new HashMap<>();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int rgba       = colorMap.getPixel(x, y);
//                int pixelColor = convertRgbaToArgb(rgba);
//
//                // Check if we already retrieved this tile
//                Pixmap tile = tileCache.get(pixelColor);
//                if (tile == null) {
//                    tile = tileSet.getOrDefault(pixelColor, tileSet.get(voidColor));
//                    tileCache.put(pixelColor, tile);
//                }
//
//                result.drawPixmap(tile, x * tileSize, y * tileSize);
//            }
//        }
//
//        return result;
//    }
//
//    private static int convertRgbaToArgb(int rgba) {
//        int r = (rgba >> 24) & 0xFF;
//        int g = (rgba >> 16) & 0xFF;
//        int b = (rgba >> 8) & 0xFF;
//        int a = rgba & 0xFF;
//        return (a << 24) | (r << 16) | (g << 8) | b;
//    }
//}
public class TileMapBuilder {

    /**
     * Builds a Pixmap using tile graphics based on the input color map.
     *
     * @param colorMap  A Pixmap where each pixel color represents a tile type
     * @param tileSet   A map from pixel ARGB color (int) to tile Pixmaps
     * @param tileSize  The tile size (typically 16)
     * @param voidColor The fallback color to use when a tile is missing
     * @return A new Pixmap composed of the actual tile graphics
     */
    public static Pixmap buildTileMapPixmap(Pixmap colorMap, Map<Integer, Pixmap> tileSet, int tileSize, int voidColor) {
        int tileCols = colorMap.getWidth();
        int tileRows = colorMap.getHeight();

        int width = tileCols * tileSize;
        int height = tileRows * tileSize;

        Pixmap result = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < tileRows; y++) {
            for (int x = 0; x < tileCols; x++) {
                int rgba = colorMap.getPixel(x, y);
                int r = (rgba >> 24) & 0xFF;
                int g = (rgba >> 16) & 0xFF;
                int b = (rgba >> 8)  & 0xFF;
                int a = rgba & 0xFF;
                int pixelColor = (a << 24) | (r << 16) | (g << 8) | b;

                if (!tileSet.containsKey(pixelColor)) {
                    System.out.printf("Unknown pixel color: 0x%08X at (%d, %d)%n", pixelColor, x, y);
                }

                Pixmap tilePixmap = tileSet.getOrDefault(pixelColor, tileSet.get(voidColor));
                if (tilePixmap != null) {
                    result.drawPixmap(tilePixmap, x * tileSize, y * tileSize);
                }
            }
        }

        return result;
    }
}
