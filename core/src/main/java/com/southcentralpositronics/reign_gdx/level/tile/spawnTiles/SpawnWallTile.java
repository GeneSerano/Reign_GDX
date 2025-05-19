package com.southcentralpositronics.reign_gdx.level.tile.spawnTiles;


import com.southcentralpositronics.reign_gdx.level.tile.Tile;

public class SpawnWallTile extends Tile {

	public SpawnWallTile(Sprite sprite) {
		super(sprite);
	}

	public boolean solid() {
		return true;
	}
}
