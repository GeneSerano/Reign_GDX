package com.southcentralpositronics.reign_gdx.level.tile;

import com.southcentralpositronics.reign_gdx.graphics.Screen;


public class voidTile extends Tile {

	public static final int color = 0x280a2b; // Very dark grayish red

	public voidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}
