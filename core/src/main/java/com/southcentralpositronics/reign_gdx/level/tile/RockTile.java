package com.southcentralpositronics.reign_gdx.level.tile;

import com.southcentralpositronics.reign_gdx.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}

	public boolean solid() {
		return false;
	}
}
