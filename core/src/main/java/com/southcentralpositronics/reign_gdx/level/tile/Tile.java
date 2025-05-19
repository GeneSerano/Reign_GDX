package com.southcentralpositronics.reign_gdx.level.tile;

import com.southcentralpositronics.reign_gdx.graphics.Screen;

import com.southcentralpositronics.reign_gdx.level.tile.spawnTiles.*;

import java.awt.*;


public class Tile {
	public int x, y;
	public         Sprite   sprite;

	public final static int pixelColor_spawnGrassTile   = 0xff125b0a;
	public final static int pixelColor_spawnHedgeTile   = 0xff004b00;
	public final static int pixelColor_spawnWaterTile   = 0xffc4dbff;
	public final static int pixelColor_spawnWallTile    = 0xff665c09;
	public final static int pixelColor_spawnWallAltTile = 0xff6c3279;
	public final static int pixelColor_spawnFloorTile   = 0xff434343;

	public final static Tile voidTile         = new voidTile(Sprite.voidSprite);
	public final static Tile spawnGrassTile   = new SpawnGrassTile(Sprite.spawnGrassSprite);
	public final static Tile spawnHedgeTile   = new SpawnHedgeTile(Sprite.spawnHedgeSprite);
	public final static Tile spawnWaterTile   = new SpawnWaterTile(Sprite.spawnWaterSprite);
	public final static Tile spawnWallTile    = new SpawnWallTile(Sprite.spawnWallSprite);
	public final static Tile spawnWallAltTile = new SpawnWallTile(Sprite.spawnWallAltSprite);
	public final static Tile spawnFloorTile   = new SpawnFloorTile(Sprite.spawnFloorSprite);


//	public static Color mmColor_spawnGrassTile   = getAverageColor(spawnGrassTile.sprite.pixels);
//	public static Color mmColor_spawnHedgeTile   = getAverageColor(spawnHedgeTile.sprite.pixels);
//	public static Color mmColor_spawnWaterTile   = getAverageColor(spawnWaterTile.sprite.pixels);
//	public static Color mmColor_spawnWallTile    = getAverageColor(spawnWallTile.sprite.pixels);
//	public static Color mmColor_spawnWallAltTile = getAverageColor(spawnWallAltTile.sprite.pixels);
//	public static Color mmColor_spawnFloorTile   = getAverageColor(spawnFloorTile.sprite.pixels);

	public final static Color mmColor_spawnGrassTile   = new Color(pixelColor_spawnGrassTile);
	public final static Color mmColor_spawnHedgeTile   = new Color(pixelColor_spawnHedgeTile);
	public final static Color mmColor_spawnWaterTile   = new Color(pixelColor_spawnWaterTile);
	public final static Color mmColor_spawnWallTile    = new Color(pixelColor_spawnWallTile);
	public final static Color mmColor_spawnWallAltTile = new Color(pixelColor_spawnWallAltTile);
	public final static Color mmColor_spawnFloorTile   = new Color(pixelColor_spawnFloorTile);


	public Tile(Sprite sprite) {
		this.sprite = sprite;


	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

	public boolean solid() {
		return false;
	}

	public static void generateMap() {
	}
}
