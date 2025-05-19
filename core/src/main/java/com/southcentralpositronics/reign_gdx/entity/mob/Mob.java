package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.Game;
import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.entity.projectile.SpellProjectile;
import com.southcentralpositronics.reign_gdx.entity.spawner.ParticleSpawner;
import com.southcentralpositronics.reign_gdx.graphics.AnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.Screen;
import com.southcentralpositronics.reign_gdx.util.Vector2i;

import java.util.List;

public class Mob extends Entity {
	protected static int            DETECTION_DISTANCE = 100;
	protected final  int            collisionOffsetX;
	protected final  int            collisionOffsetY;
	protected        boolean        walking            = false;
	protected        AnimatedSprite animSprite         = null;
	protected        Direction      dir                = Direction.DOWN; // start facing camera
	//	protected        List<Projectile> projectiles        = new ArrayList<Projectile>();
	protected        double         health;
	protected        AnimatedSprite mobUp;
	protected        AnimatedSprite mobDown;
	protected        AnimatedSprite mobLeft;
	protected        AnimatedSprite mobRight;

	public Mob() {
		collisionOffsetX = -24;
		collisionOffsetY = -32;
	}

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = Direction.RIGHT; // Player facing east
		if (xa < 0) dir = Direction.LEFT; // Player facing west
		if (ya > 0) dir = Direction.DOWN; // Player facing south
		if (ya < 0) dir = Direction.UP; // Player facing north

		for (int x = 0; x < Math.abs(xa); x++) {
			if (!collision(abs(xa), ya)) {
				this.x += abs(xa);
			}
		}

		for (int y = 0; y < Math.abs(ya); y++) {
			if (!collision(xa, abs(ya))) {
				this.y += abs(ya);
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		else return 1;
	}

	protected void shootAtPos(int x, int y, double angle) {
		Projectile projectile = new SpellProjectile(x, y - 16, angle, this);
//		projectiles.add(projectile);
		level.add(projectile);

	}

	private void shootRandom() {
		boolean fireTest = (Game.updateInt % Projectile.SHOT_DELAY == 0); //== 0 || level.getProjectiles().size() == 0);
		if (fireTest) {
			List<PlayerMob> players = level.getPlayersInRadius(this, Mob.DETECTION_DISTANCE);
			int             index   = random.nextInt(players.size());
			PlayerMob       player  = players.get(index);

			if (player != null) {
				double dx          = player.getX() - x;
				double dy          = player.getY() - y;
				double firingAngle = Math.atan2(dy, dx);
				shootAtPos((int) x, (int) y, firingAngle);
			}
		}
	}

	protected void shootClosest() {
		boolean fireTest = (Game.updateInt % Projectile.SHOT_DELAY == 0); //== 0 || level.getProjectiles().size() == 0);
		if (fireTest) {
			PlayerMob       closestPlayer = null;
			List<PlayerMob> players       = level.getPlayersInRadius(this, Mob.DETECTION_DISTANCE);
			double          min           = 0;

			for (int i = 0; i < players.size(); i++) {
				PlayerMob player   = players.get(i);
				Vector2i  v1       = new Vector2i((int) x, (int) y);
				Vector2i  v2       = new Vector2i((int) player.getX(), (int) player.y);
				double    distance = Vector2i.getDistance(v1, v2);

				if (i == 0 || distance < min) {
					min           = distance;
					closestPlayer = player;
				}
			}

			if (closestPlayer != null) {
				double dx          = closestPlayer.getX() - x;
				double dy          = closestPlayer.getY() - y;
				double firingAngle = Math.atan2(dy, dx);
				shootAtPos((int) x, (int) y, firingAngle);
			}
		}
	}

	public void render(Screen screen) {

		sprite = animSprite.getSprite();
		var posX = (int) (x + collisionOffsetX);
		var posY = (int) (y + collisionOffsetY);
		screen.renderMob(posX, posY, this);

	}

	protected boolean collision(double xa, double ya) {
//		boolean solid = false;
//		int     ix, iy;
//
//		for (int c = 0; c < 4; c++) {
//			double xt = ((x + xa) - c % 2 * 14) / 16;
//			double yt = ((y + ya) - (double) c / 2 * 14) / 16;
//
//			if (xa == -1) xt -= 1;
//			if (ya == -1) yt -= 0;
//
//			if (c % 2 == 0) {
//				ix = (int) Math.floor(xt); // --->
//			} else {
//				ix = (int) Math.ceil(xt); // <---
//			}
//			if (c / 2 == 0) {
//				iy = (int) Math.floor(yt);
//			} else {
//				iy = (int) Math.ceil(yt);
//			}
//
//			if (Game.DEBUG && this instanceof PlayerMob) {
//				System.out.println("Tile Cord: " + ix + "," + iy);
//			}
//			if (level.getTile(ix, iy).solid()) solid = true;
//		}
//		return solid;
		return level.tileCollision(x, y, 14, xa, ya, 0, 0);
	}

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT

	}

	public void remove() {
		// Remove from level
		super.remove();
		new ParticleSpawner(x, y, 0, 10, 100, level);
	}

//	protected void checkForHit() {
//		List<Projectile> projectileList = level.getProjectiles();
//		int              clipDist       = 16;
//
//		for (int i = 0; i < projectileList.size(); i++) {
//			Projectile projectile  = projectileList.get(i);
//			var        xDist       = (x + collisionOffsetX) - (projectile.getX() + projectile.collisionOffsetX) + 8;
//			var        yDist       = (y + collisionOffsetY) - (projectile.getY() + projectile.collisionOffsetY) + 16;
//			int        playerDistX = (int) Math.abs(xDist);
//			int        playerDistY = (int) Math.abs(yDist);
//			System.out.print("X: " + playerDistX);
//			System.out.println(" Y: " + playerDistY);
//			if (this != projectile.getFiredBy()) {
//				if (playerDistX < clipDist && playerDistY < clipDist) {
//					projectile.hitTarget();
//				}
//			}
//		}
//	}
}
