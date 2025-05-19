package com.southcentralpositronics.reign_gdx.level;

import com.southcentralpositronics.reign_gdx.level.tile.Tile;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
		generateMobs();
	}

	public void update() {
		super.update();
		if (mobs.isEmpty()) generateMobs();
	}

	@Override
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnGrassTile) return Tile.spawnGrassTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnHedgeTile) return Tile.spawnHedgeTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnWaterTile) return Tile.spawnWaterTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnWallTile) return Tile.spawnWallTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnWallAltTile) return Tile.spawnWallAltTile;
		if (tiles[x + y * width] == Tile.pixelColor_spawnFloorTile) return Tile.spawnFloorTile;

		return Tile.voidTile;

	}

	@Override
	protected void generateLevel() {


    }
}
