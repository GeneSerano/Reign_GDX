package com.southcentralpositronics.reign_gdx.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.level.Level;

import java.util.Random;

public class Entity {
	protected final Random  random  = new Random();
	protected       double  x;
	protected double y;
	protected Sprite sprite;
	protected Level  level;
	protected boolean removed = false;
	protected Type    type;

	public Entity() {

	}

	public Entity(double x, double y, Sprite sprite) {
		this.x      = x;
		this.y      = y;
		this.sprite = sprite;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void update() {

	}

	public void init(Level level) {
		this.level = level;
	}

	public void render(Screen screen) {

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public enum Type {
		ENEMY, PLAYER
	}

	public Type getType() {
		return type;
	}
}
