package com.southcentralpositronics.reign_gdx.util;

public class Vector2i {

	public int x;
	public int y;

	public Vector2i() {
		set(0, 0);
	}

	public Vector2i(Vector2i vector2i) {
		set(vector2i.x, vector2i.y);
	}

	public Vector2i(int x, int y) {
		set(x, y);
	}

	public Vector2i(int x, int y, int scale) {
		set(x * scale, y * scale);
	}

	public static double getDistance(Vector2i start, Vector2i goal) {
		double dx        = start.x - goal.x;
		double dy        = start.y - goal.y;
		double dxSquared = dx * dx;
		double dySquared = dy * dy;
		return Math.sqrt(dxSquared + dySquared);
	}

	private void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i modify(int value) {
		Vector2i vector = new Vector2i(this.x + value, this.y + value);
		return this.modify(vector);
	}

	public Vector2i modify(Vector2i vector2i) {
		this.x += vector2i.x;
		this.y += vector2i.y;
		return this;

	}

	public Vector2i offsetFromBy(int value) {
		Vector2i vector = new Vector2i(this.x + value, this.y + value);
		return this.modify(vector);
	}

	public Vector2i offsetFromBy(int x, int y) {
		Vector2i vector = new Vector2i(this.x + x, this.y + y);
		return this.modify(vector);
	}

	public Vector2i offsetFromBy(Vector2i vector2i) {
		int xo = this.x + vector2i.x;
		int yo = this.y + vector2i.y;
		return new Vector2i(xo, yo);

	}

//	public Vector2i subtract(int value) {
//		Vector2i vector = new Vector2i(this.x - value, this.y - value);
//		return this.subtract(vector);
//	}
//
//	public Vector2i subtract(Vector2i vector2i) {
//		int xo = this.x - vector2i.x;
//		int yo = this.y - vector2i.y;
//		return new Vector2i(xo, yo);
//
//	}

//	public int getX() {
//		return x;
//	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

//	public int getY() {
//		return y;
//	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Vector2i vec)) return false;
		return vec.x == this.x && vec.y == this.y;
	}
}
