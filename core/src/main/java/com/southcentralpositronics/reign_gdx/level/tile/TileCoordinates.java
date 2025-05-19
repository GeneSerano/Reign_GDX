package com.southcentralpositronics.reign_gdx.level.tile;

public class TileCoordinates {

	private final int TILE_SIZE = 16;
	private final int x;
	private final int y;

	public TileCoordinates(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int[] getXY() {
		int[] r = new int[2];
		r[0] = x;
		r[1] = y;
		return r;
	}
}
