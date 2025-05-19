package com.southcentralpositronics.reign_gdx.level.tile.spawnTiles;

import com.southcentralpositronics.reign_gdx.graphics.Sprite;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

public class SpawnWaterTile extends Tile {

	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}

	public boolean solid() {
		return false;
	}
}
