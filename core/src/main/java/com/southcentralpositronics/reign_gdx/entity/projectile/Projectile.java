package com.southcentralpositronics.reign_gdx.entity.projectile;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.graphics.Screen;

import com.southcentralpositronics.reign_gdx.level.Level;

public abstract class Projectile extends Entity {
	public final static int    SHOT_DELAY = 8;
	protected final     double xOrig, yOrig;
	protected double x, y, nx, ny, angle, speed, range, damage;
	protected Sprite sprite;
	protected Entity firedBy;
	public    int    collisionOffsetX = -12;
	public    int    collisionOffsetY = -2;


	public Projectile(double x, double y, double direction) {
//        super();
		xOrig  = x;
		yOrig  = y;
		angle  = direction;
		this.x = x;
		this.y = y;

	}

	public void update() {
		int nextX = (int) (x + nx);
		int nextY = (int) (y + ny);
		boolean collision = level.tileCollision(x, y, 7, nx, ny, -8, -8); //xO5yO4
		if (collision) remove();
		if (!removed) move();
	}

	public double getSpriteSize() {
		return sprite.SIZE;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void init(Level level) {

		this.level = level;
	}

	public Entity getFiredBy() {
		return firedBy;
	}

	protected void move() {
		x += nx;
		y += ny;

		if (distanceTraveled() > range) {
			remove();
		}
	}

	public void hitTarget() {
		// Remove from level
		level.add(new ParticleSpawner(x, y, angle,10, 1000, level));
		removed = true;
	}

	public void remove() {
		// Remove from level
		level.add(new ParticleSpawner(x, y, angle,10, 250, level));
		removed = true;
	}

	private double distanceTraveled() {
		double dist = 0;
		double xDist, yDist, xyDist;
		xDist  = Math.pow(x - xOrig, 2);
		yDist  = Math.pow(y - yOrig, 2);
		xyDist = Math.abs(xDist + yDist);
		dist   = Math.sqrt(xyDist);
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x + collisionOffsetX, (int) y + collisionOffsetY, this);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
