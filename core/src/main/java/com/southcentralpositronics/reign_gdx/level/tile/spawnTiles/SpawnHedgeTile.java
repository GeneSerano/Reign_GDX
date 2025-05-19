package com.southcentralpositronics.reign_gdx.level.tile.spawnTiles;

import com.southcentralpositronics.reign_gdx.graphics.Sprite;
import com.southcentralpositronics.reign_gdx.level.tile.Tile;

public class SpawnHedgeTile extends Tile {

	public SpawnHedgeTile(Sprite sprite) {
		super(sprite);
	}

	public boolean solid() {
		return true;
	}

}
