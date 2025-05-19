package com.southcentralpositronics.reign_gdx.entity.mob;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.entity.projectile.Projectile;
import com.southcentralpositronics.reign_gdx.graphics.AnimatedSprite;
import com.southcentralpositronics.reign_gdx.graphics.SpriteSheet;

import java.util.List;

public class ShooterMob extends Mob {
	private int ya   = random.nextInt(3) - 1;
	private int xa   = random.nextInt(3) - 1;
	private int time = 0;

	public ShooterMob(int x, int y) {
		mobUp           = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
		mobDown         = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
		mobLeft         = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
		mobRight        = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
		this.x          = x << 4;
		this.y          = y << 4;
		this.animSprite = mobDown;
		type            = Type.ENEMY;
		health          = 1.0;
	}

	public void update() {
		time++;
		shootClosest();
		checkForHit();

		if (time % random.nextInt(30, 60) == 0) {
			if (random.nextBoolean()) {
				ya = random.nextInt(3) - 1;
				xa = 0;
			} else {
				ya = 0;
				xa = random.nextInt(3) - 1;
			}
		}

		if (xa != 0 || ya != 0) {
			if (collision(0, ya * 2)) {
				ya *= -1;
			}
			if (collision(xa * 2, 0)) {
				xa *= -1;
			}
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}


		if (ya < 0) {
			dir        = Direction.UP;
			animSprite = mobUp;
		} else if (ya > 0) {
			dir        = Direction.DOWN;
			animSprite = mobDown;
		}
		if (xa < 0) {
			dir        = Direction.LEFT;
			animSprite = mobLeft;
		}
		if (xa > 0) {
			dir        = Direction.RIGHT;
			animSprite = mobRight;
		}
	}

	private void checkForHit() {
		List<Projectile> projectileList = level.getProjectiles();
		int              clipDist       = 16;

		for (int i = 0; i < projectileList.size(); i++) {
			Projectile projectile  = projectileList.get(i);
			var        xDist       = (x + collisionOffsetX) - (projectile.getX() + projectile.collisionOffsetX) + 8;
			var        yDist       = (y + collisionOffsetY) - (projectile.getY() + projectile.collisionOffsetY) + 16;
			int        playerDistX = (int) Math.abs(xDist);
			int        playerDistY = (int) Math.abs(yDist);
			Entity     parent      = projectile.getFiredBy();
			if ((parent instanceof PlayerMob)) {
				if (playerDistX < clipDist && playerDistY < clipDist) {
					projectile.hitTarget();
					health -= 0.1;
					if (health <= 0.0) {
						this.remove();
						PlayerMob player = (PlayerMob) parent;
						player.scoredKill();
					}
				}
			}
		}
	}
}
